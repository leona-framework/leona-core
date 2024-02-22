package com.sylvona.leona.core.commons.streams;

import java.util.List;
import java.util.function.IntFunction;
import java.util.stream.Stream;

/**
 * A "stream" extending {@link LINQStream} utilized when a stream must be eagerly captured into a list.
 *
 * @param <T> the type of the stream.
 *
 * @see LINQStream
 *
 * @author Evan Cowin
 * @since 0.0.1
 */
class ListCollectedStream<T> extends LINQStream<T> {
    private final List<T> list;

    ListCollectedStream(Stream<T> stream, List<T> list) {
        super(stream);
        this.list = list;
    }

    @Override
    public List<T> toList() {
        return list;
    }

    @Override
    public Object[] toArray() {
        return list.toArray();
    }

    @Override
    public <A> A[] toArray(IntFunction<A[]> generator) {
        return list.toArray(generator);
    }
}
