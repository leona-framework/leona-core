package com.sylvona.leona.core.commons.containers;

import com.sylvona.leona.core.commons.streams.LINQ;
import com.sylvona.leona.core.commons.streams.LINQStream;

import java.util.Iterator;

/**
 * Contains various functions for manipulation and operating upon {@link Tuple Tuples}.
 *
 * @author Evan Cowin
 * @see Tuple
 * @since 0.0.1
 */
public class TupleOperators {
    /**
     * Unpacks a tuple's elements into a {@link LINQStream}.
     *
     * @param packedTuple The tuple to unpack.
     * @param prioritizeRight If true, unpacks the right element before the left element; otherwise, unpacks the left element first.
     * @return A {@link LINQStream} containing the unpacked elements.
     */
    public static LINQStream<Object> unpack(Tuple<?, ?> packedTuple, boolean prioritizeRight) {
        return LINQ.stream(new TupleIterator(packedTuple, prioritizeRight));
    }

    /**
     * Unpacks a tuple's elements into a {@link LINQStream}, prioritizing the left element first.
     *
     * @param packedTuple The tuple to unpack.
     * @return A {@link LINQStream} containing the unpacked elements.
     */
    public static LINQStream<Object> unpack(Tuple<?, ?> packedTuple) {
        return unpack(packedTuple, false);
    }

    /**
     * An iterator implementation for unpacking a tuple's elements.
     */
    public static class TupleIterator implements Iterable<Object>, Iterator<Object> {
        private final boolean rightSided;
        private Object standby;

        private TupleIterator(Tuple<?, ?> packedTuple, boolean rightSided) {
            standby = packedTuple;
            this.rightSided = rightSided;
        }

        private TupleIterator(Tuple<?, ?> packedTuple) {
            this(packedTuple, false);
        }

        @Override
        public Iterator<Object> iterator() {
            return this;
        }

        @Override
        public boolean hasNext() {
            return standby != null;
        }

        @Override
        public Object next() {
            if (standby instanceof Tuple<?, ?> tuple) {
                standby = rightSided ? tuple.item2() : tuple.item1();
                return rightSided ? tuple.item1() : tuple.item2();
            }

            Object value = standby;
            standby = null;
            return value;
        }
    }
}
