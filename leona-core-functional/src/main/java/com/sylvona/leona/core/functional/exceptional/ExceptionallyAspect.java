package com.sylvona.leona.core.functional.exceptional;

import com.sylvona.leona.core.functional.annotations.Exceptionally;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@RequiredArgsConstructor
public class ExceptionallyAspect {
    private final ExceptionalHandler exceptionalHandler;

    @Pointcut("execution(@com.sylvona.leona.core.functional.annotations.Exceptionally com.sylvona.leona.core.functional.exceptional.Exceptional<?> *(..))")
    public void findMethodsMarkedWithEitherOr() {}

    @Around("findMethodsMarkedWithEitherOr()")
    public Exceptional<?> doInterceptMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        Exceptionally exceptionally = methodSignature.getMethod().getAnnotation(Exceptionally.class);
        Class<? extends Throwable>[] targetExceptions = exceptionally.value();
        boolean hasTarget = targetExceptions.length > 0 && !targetExceptions[0].equals(Throwable.class);

        try {
            Exceptional<?> returnValue = (Exceptional<?>) joinPoint.proceed();
            // "Smart" way of binding an exception handler to an exception, this allows for the call of Exception.respond()
            returnValue.attachedHandler = exceptionalHandler;
            return returnValue;
        } catch (ShortCircuitingResponseException shortCircuit) { // ShortCircuiting means we'll skip the exceptional handling and immediately throw the inner exception
            throw shortCircuit.getInnerException();
        } catch (Throwable e) {

            if (hasTarget) {
                if (Arrays.stream(targetExceptions).anyMatch(target -> target.isInstance(e))) {
                    return Exceptional.error(e);
                }
            }

            if (e instanceof RuntimeException rte) {
                return Exceptional.error(rte);
            }

            throw e;
        }
    }
}
