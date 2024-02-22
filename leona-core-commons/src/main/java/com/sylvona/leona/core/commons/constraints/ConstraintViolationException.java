package com.sylvona.leona.core.commons.constraints;

/**
 * Exception thrown when a constraint is violated.
 */
public class ConstraintViolationException extends RuntimeException {
    /**
     * The enforcer that detected the violation.
     */
    final Constraints.Enforcer<?> enforcer;

    /**
     * Constructs a new ConstraintViolationException with the specified enforcer and detail message.
     *
     * @param enforcer the enforcer that detected the violation
     * @param message  the detail message (which is saved for later retrieval by the {@link #getMessage()} method)
     */
    ConstraintViolationException(Constraints.Enforcer<?> enforcer, String message) {
        super(message);
        this.enforcer = enforcer;
    }

    /**
     * Constructs a new ConstraintViolationException with the specified message.
     *
     * @param message the detail message.
     */
    public ConstraintViolationException(String message) {
        this(null, message);
    }
}
