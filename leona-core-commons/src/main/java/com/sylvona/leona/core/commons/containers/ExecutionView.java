package com.sylvona.leona.core.commons.containers;

import java.time.Duration;

/**
 * An interface representing the result of an execution, which can be either a successful result of type T
 * or an error represented by a Throwable. Additionally, it provides information about the overall execution time.
 *
 * @param <T> The type of the successful result.
 *
 * @author Evan Cowin
 * @since 0.0.1
 */
public interface ExecutionView<T> extends Either<T, Throwable> {

    /**
     * Retrieves the execution time of the operation.
     *
     * @return The duration representing the execution time.
     */
    Duration executionTime();

    T result();

    Throwable error();

    @Override
    default T left() {
        return result();
    }

    @Override
    default Throwable right() {
        return error();
    }
}
