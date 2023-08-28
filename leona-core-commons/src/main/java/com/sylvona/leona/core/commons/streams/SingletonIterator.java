package com.sylvona.leona.core.commons.streams;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * An iterator implementation that represents a singleton sequence containing a single item.
 * This iterator produces the single item and then becomes exhausted.
 *
 * @param <T> The type of the item in the singleton sequence.
 */
@RequiredArgsConstructor(staticName = "of")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SingletonIterator<T> implements Iterator<T>, Iterable<T> {
    private final T item;
    private boolean consumed;

    /**
     * Creates an empty singleton iterator.
     *
     * @param <T> The type of the iterator (implicitly determined as null).
     * @return An empty singleton iterator.
     */
    public static <T> SingletonIterator<T> empty() {
        return new SingletonIterator<>(null, true);
    }

    /**
     * Provides the iterator itself as an Iterable for enhanced for-loop usage.
     *
     * @return This singleton iterator as an Iterable.
     */
    @Override
    public @NotNull Iterator<T> iterator() {
        return this;
    }

    /**
     * Checks if there is an unconsumed item in the iterator.
     *
     * @return {@code true} if an unconsumed item is present, {@code false} otherwise.
     */
    @Override
    public boolean hasNext() {
        return !consumed;
    }

    /**
     * Retrieves the next item in the singleton sequence and marks the iterator as consumed.
     *
     * @return The next item in the sequence.
     * @throws NoSuchElementException If there are no more items to retrieve.
     */
    @Override
    public T next() {
        if (consumed) {
            throw new NoSuchElementException("No more elements in the iterator.");
        }
        consumed = true;
        return item;
    }
}

