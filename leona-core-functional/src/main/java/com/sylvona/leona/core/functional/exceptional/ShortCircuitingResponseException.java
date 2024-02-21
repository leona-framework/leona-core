package com.sylvona.leona.core.functional.exceptional;

public class ShortCircuitingResponseException extends RuntimeException {
    private final RuntimeException innerException;

    private ShortCircuitingResponseException(RuntimeException runtimeException) {
        this.innerException = runtimeException;
    }

    public static ShortCircuitingResponseException wrap(RuntimeException runtimeException) {
        return new ShortCircuitingResponseException(runtimeException);
    }

    RuntimeException getInnerException() {
        return this.innerException;
    }
}
