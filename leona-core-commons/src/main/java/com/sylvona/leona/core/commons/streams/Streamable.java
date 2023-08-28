package com.sylvona.leona.core.commons.streams;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * An interface for creating streamable sequences of elements.
 *
 * @param <T> The type of elements in the stream.
 */
public interface Streamable<T> extends Iterable<T> {

    /**
     * Creates a sequential {@link Stream} from the elements of this streamable.
     *
     * @return A sequential stream of elements.
     */
    default Stream<T> stream() {
        return StreamSupport.stream(spliterator(), false);
    }

    /**
     * Creates a parallel {@link Stream} from the elements of this streamable.
     *
     * @return A parallel stream of elements.
     */
    default Stream<T> parallelStream() {
        return StreamSupport.stream(spliterator(), true);
    }
}