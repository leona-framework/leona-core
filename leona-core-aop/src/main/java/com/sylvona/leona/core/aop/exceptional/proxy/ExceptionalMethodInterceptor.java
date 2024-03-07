package com.sylvona.leona.core.aop.exceptional.proxy;

import com.sylvona.leona.core.aop.exceptional.AnnotatedMethodInvoker;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

class ExceptionalMethodInterceptor implements MethodInterceptor {
    private final AnnotatedMethodInvoker annotatedMethodInvoker;

    public ExceptionalMethodInterceptor(AnnotatedMethodInvoker annotatedMethodInvoker) {
        this.annotatedMethodInvoker = annotatedMethodInvoker;
    }

    @Nullable
    @Override
    public Object invoke(@NotNull MethodInvocation invocation) throws Throwable {
        return annotatedMethodInvoker.invoke(invocation.getMethod(), invocation::proceed);
    }
}
