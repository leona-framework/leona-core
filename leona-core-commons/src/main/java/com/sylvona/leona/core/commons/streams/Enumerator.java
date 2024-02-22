package com.sylvona.leona.core.commons.streams;

import com.sylvona.leona.core.commons.containers.Enumerated;
import com.sylvona.leona.core.commons.containers.Tuple;

import java.util.function.BiFunction;

/**
 * A functional interface for enumerating elements of type {@code T} with an index, producing a result of type {@code R}.
 *
 * @param <T> The type of elements to be enumerated.
 * @param <R> The type of the result produced by the enumeration.
 */
@FunctionalInterface
public interface Enumerator<T, R> extends BiFunction<Integer, T, R> {
    /**
     * Applies this enumeration function to the index and value stored in the given {@link Enumerated}.
     *
     * @param enumeratedTuple The enumerated tuple containing the index and value.
     * @return The result of applying this enumeration function to the tuple.
     */
    default R apply(Tuple<Integer, T> enumeratedTuple) {
        return enumeratedTuple.compose(this);
    }
}
