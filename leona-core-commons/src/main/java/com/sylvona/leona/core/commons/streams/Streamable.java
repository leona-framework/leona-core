package com.sylvona.leona.core.commons.streams;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;


/**
 * An interface for objects that can be streamed as a sequence of elements.
 *
 * @param <T> The type of elements in the stream.
 * @author Evan Cowin
 * @see Stream
 * @since 0.0.1
 */
public interface Streamable<T> extends Iterable<T> {
    /**
     * Returns a sequential {@link Stream} of elements from this object.
     *
     * @return a sequential {@link Stream} over the elements in this object
     * @see Stream
     */
    default Stream<T> stream() {
        return StreamSupport.stream(spliterator(), false);
    }

    /**
     * Returns a possibly parallel {@link Stream} of elements from this object.
     *
     * @return a possibly parallel {@link Stream} over the elements in this object
     */
    default Stream<T> parallelStream() {
        return StreamSupport.stream(spliterator(), true);
    }
}

