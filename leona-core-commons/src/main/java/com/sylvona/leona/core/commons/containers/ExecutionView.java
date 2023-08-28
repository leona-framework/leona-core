package com.sylvona.leona.core.commons.containers;

import java.time.Duration;

/**
 * An interface representing the result of an execution, which can be either a successful result of type {@code T}
 * or an error represented by a {@link Throwable}. Additionally, it provides information about the overall execution time.
 *
 * @param <T> The type of the successful result.
 */
public interface ExecutionView<T> extends Either<T, Throwable> {

    /**
     * Retrieves the duration representing the execution time of the operation.
     *
     * @return The duration of the execution time.
     */
    Duration executionTime();

    /**
     * Retrieves the successful result of the execution.
     *
     * @return The successful result of type {@code T}.
     */
    T result();

    /**
     * Retrieves the error that occurred during the execution.
     *
     * @return The error represented by a {@link Throwable}.
     */
    Throwable error();

    /**
     * Returns the left value, which is the successful result.
     *
     * @return The successful result of type {@code T}.
     */
    @Override
    default T left() {
        return result();
    }

    /**
     * Returns the right value, which is the error.
     *
     * @return The error represented by a {@link Throwable}.
     */
    @Override
    default Throwable right() {
        return error();
    }
}