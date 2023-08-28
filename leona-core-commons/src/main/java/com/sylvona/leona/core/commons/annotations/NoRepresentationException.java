package com.sylvona.leona.core.commons.annotations;

/**
 * Exception thrown when an annotation marked with the {@link Represents} annotation lacks a defined representation class.
 */
public class NoRepresentationException extends RuntimeException {

    /**
     * Constructs a new {@code NoRepresentationException} instance with the specified error message.
     *
     * @param message The error message describing the exception.
     */
    public NoRepresentationException(String message) {
        super(message);
    }
}
