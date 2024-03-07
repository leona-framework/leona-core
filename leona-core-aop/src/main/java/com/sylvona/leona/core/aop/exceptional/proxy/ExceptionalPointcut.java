package com.sylvona.leona.core.aop.exceptional.proxy;

import com.sylvona.leona.core.aop.annotations.Exceptionally;
import com.sylvona.leona.core.commons.containers.Either;
import com.sylvona.leona.core.utils.AnnotationHelper;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.support.StaticMethodMatcherPointcut;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Arrays;

class ExceptionalPointcut extends StaticMethodMatcherPointcut implements Serializable {
    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        return isValidExceptionalSignature(method);
    }

    @Override
    public ClassFilter getClassFilter() {
        return new ExceptionalClassFilter();
    }

    private static class ExceptionalClassFilter implements ClassFilter {

        @Override
        public boolean matches(Class<?> clazz) {
            return Arrays.stream(clazz.getMethods()).anyMatch(ExceptionalPointcut::isValidExceptionalSignature);
        }
    }

    private static boolean isValidExceptionalSignature(Method method) {
        return AnnotationHelper.hasAnnotation(method, Exceptionally.class) && Either.class.isAssignableFrom(method.getReturnType());
    }
}
