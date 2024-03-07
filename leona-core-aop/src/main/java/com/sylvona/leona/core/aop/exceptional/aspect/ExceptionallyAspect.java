package com.sylvona.leona.core.aop.exceptional.aspect;

import com.sylvona.leona.core.aop.exceptional.AnnotatedMethodInvoker;
import com.sylvona.leona.core.aop.exceptional.Exceptional;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ExceptionallyAspect  {
    private final AnnotatedMethodInvoker annotatedMethodInvoker;

    public ExceptionallyAspect(AnnotatedMethodInvoker annotatedMethodInvoker) {
        this.annotatedMethodInvoker = annotatedMethodInvoker;
    }

    @Pointcut("execution(@com.sylvona.leona.core.aop.annotations.Exceptionally com.sylvona.leona.core.aop.exceptional.Exceptional<?> *(..))")
    public void findMethodsMarkedWithEitherOr() {
    }

    @Around("findMethodsMarkedWithEitherOr()")
    public Exceptional<?> doInterceptMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        return (Exceptional<?>) annotatedMethodInvoker.invoke(((MethodSignature) joinPoint.getSignature()).getMethod(), joinPoint::proceed);
    }


}
