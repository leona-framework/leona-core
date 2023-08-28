package com.sylvona.leona.core.commons.exceptions;

/**
 * Runtime wrapper for the {@link ReflectiveOperationException} exception.
 * <p>
 * <i>From the exception's javadoc:</i>
 * <p>
 * Common superclass of exceptions thrown by reflective operations in core reflection.
 * @see ReflectiveOperationException
 */
public class ReflectiveOperationRuntimeException extends RuntimeException {
    /**
     * Constructs a new {@code ReflectiveOperationRuntimeException} without any detail message.
     */
    public ReflectiveOperationRuntimeException() {
    }

    /**
     * Constructs a new {@code ReflectiveOperationRuntimeException} with the specified detail message.
     *
     * @param message The detail message.
     */
    public ReflectiveOperationRuntimeException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code ReflectiveOperationRuntimeException} with the specified detail message
     * and cause.
     *
     * @param message The detail message.
     * @param cause   The cause (which is saved for later retrieval by the {@link #getCause()} method).
     */
    public ReflectiveOperationRuntimeException(String message, ReflectiveOperationException cause) {
        super(message, cause);
    }

    /**
     * Constructs a new {@code ReflectiveOperationRuntimeException} with the specified cause and a
     * detail message of {@code (cause==null ? null : cause.toString())} (which typically contains
     * the class and detail message of the cause).
     *
     * @param cause The cause (which is saved for later retrieval by the {@link #getCause()} method).
     * @see ReflectiveOperationException
     */
    public ReflectiveOperationRuntimeException(ReflectiveOperationException cause) {
        super(cause);
    }
}
