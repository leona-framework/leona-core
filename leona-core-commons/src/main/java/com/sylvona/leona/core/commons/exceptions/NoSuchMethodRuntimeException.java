package com.sylvona.leona.core.commons.exceptions;

/**
 * Runtime wrapper for the {@link NoSuchMethodException} exception.
 * <p>
 * <i>From the exception's javadoc:</i>
 * <p>
 * Thrown when a particular method cannot be found.
 * @see NoSuchMethodException
 */
public class NoSuchMethodRuntimeException extends RuntimeException {
    /**
     * Constructs a new {@code NoSuchMethodRuntimeException} without any detail message.
     */
    public NoSuchMethodRuntimeException() {
    }

    /**
     * Constructs a new {@code NoSuchMethodRuntimeException} with the specified detail message.
     *
     * @param message The detail message.
     */
    public NoSuchMethodRuntimeException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code NoSuchMethodRuntimeException} with the specified detail message and cause.
     *
     * @param message The detail message.
     * @param cause   The cause (which is saved for later retrieval by the {@link #getCause()} method).
     */
    public NoSuchMethodRuntimeException(String message, NoSuchMethodException cause) {
        super(message, cause);
    }

    /**
     * Constructs a new {@code NoSuchMethodRuntimeException} with the specified cause and a
     * detail message of {@code (cause==null ? null : cause.toString())} (which typically
     * contains the class and detail message of cause).
     *
     * @param cause The cause (which is saved for later retrieval by the {@link #getCause()} method).
     * @see NoSuchMethodException
     */
    public NoSuchMethodRuntimeException(NoSuchMethodException cause) {
        super(cause);
    }
}
