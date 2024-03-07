package com.sylvona.leona.core.aop.exceptional;

import com.sylvona.leona.core.aop.annotations.Exceptionally;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.function.Function;


public class AnnotatedMethodInvoker {
    private final ExceptionalHandler exceptionalHandler;

    public AnnotatedMethodInvoker(ExceptionalHandler exceptionalHandler) {
        this.exceptionalHandler = exceptionalHandler;
    }

    public Object invoke(Method method, CheckedSupplier<Object> originalInvocation) throws Throwable {
        Exceptionally exceptionally = method.getAnnotation(Exceptionally.class);
        Class<? extends Throwable>[] targetExceptions = exceptionally.value();
        boolean hasTarget = targetExceptions.length > 0 && !targetExceptions[0].equals(Throwable.class);

        Function<Throwable, Exceptional<?>> exceptionalFunction = ex -> {
            if (method.getReturnType() == WebExceptional.class) {
                return new WebExceptional<>(null, ex, exceptionalHandler);
            }
            return new Exceptional<>(null, ex, exceptionalHandler);
        };

        try {
            Exceptional<?> returnValue = (Exceptional<?>) originalInvocation.get();
            // "Smart" way of binding an exception handler to an exception, this allows for the call of Exception.respond()
            returnValue.attachedHandler = exceptionalHandler;
            return returnValue;
        } catch (
                ShortCircuitedException shortCircuit) { // ShortCircuiting means we'll skip the exceptional handling and immediately throw the inner exception
            throw shortCircuit.getInnerException();
        } catch (Throwable e) {
            if (hasTarget) {
                if (Arrays.stream(targetExceptions).anyMatch(target -> target.isInstance(e))) {
                    return exceptionalFunction.apply(e);
                }
            }

            if (e instanceof RuntimeException rte) {
                return exceptionalFunction.apply(rte);
            }

            throw e;
        }
    }

    @FunctionalInterface
    public interface CheckedSupplier<T> {
        T get() throws Throwable;
    }
}
