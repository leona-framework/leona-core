package com.sylvona.leona.core.aop.exceptional;

public class ShortCircuitedException extends RuntimeException {
    private final RuntimeException innerException;

    private ShortCircuitedException(RuntimeException runtimeException) {
        super(runtimeException);
        this.innerException = runtimeException;
    }

    public static ShortCircuitedException wrap(RuntimeException runtimeException) {
        if (runtimeException instanceof ShortCircuitedException shortCircuit) {
            return new ShortCircuitedException(shortCircuit.innerException);
        }
        return new ShortCircuitedException(runtimeException);
    }

    RuntimeException getInnerException() {
        return this.innerException;
    }
}
