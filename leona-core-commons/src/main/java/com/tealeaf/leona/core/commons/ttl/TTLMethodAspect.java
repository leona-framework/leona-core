package com.tealeaf.leona.core.commons.ttl;

import lombok.AllArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Aspect
@Component
@AllArgsConstructor
class TTLMethodAspect {
    private final TTLStore<Object> ttlStore;

    @Pointcut("execution(@com.tealeaf.leona.core.commons.ttl.TTLMethod * *(..))")
    public void findMarkedTTLMethods() {}

    @Around("findMarkedTTLMethods()")
    public Object doMarkedMethodInterception(ProceedingJoinPoint joinPoint) throws Throwable {
        TTLMethod ttlMethod = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(TTLMethod.class);

        Object source = joinPoint.getThis();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        TTLValue<?> ttlValue = ttlStore.fetchValue(source, signature);

        if (ttlValue == null || ttlValue.isExpired()) {
            Object realValue = joinPoint.proceed();
            ttlStore.storeValue(source, signature, realValue, createDuration(ttlMethod));
            return realValue;
        }

        return ttlValue.value();
    }

    private static Duration createDuration(TTLMethod ttlMethod) {
        return Duration.of(ttlMethod.value(), ttlMethod.unit().toChronoUnit());
    }
}
