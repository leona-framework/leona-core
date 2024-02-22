package com.sylvona.leona.core.commons.streams;

import jakarta.validation.constraints.NotNull;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * An iterator implementation that represents a singleton sequence containing a single item.
 * This iterator produces the single item and then becomes exhausted.
 *
 * @param <T> The type of the item in the singleton sequence.
 * @author Evan Cowin
 * @see Iterator
 * @since 0.0.1
 */
public class SingletonIterator<T> implements Iterator<T>, Iterable<T> {
    private final T item;
    private boolean consumed;

    private SingletonIterator(T item) {
        this.item = item;
    }

    private SingletonIterator(T item, boolean consumed) {
        this.item = item;
        this.consumed = consumed;
    }

    /**
     * Creates an empty instance of {@link SingletonIterator}.
     *
     * @param <T> The type of the item.
     * @return An empty {@link SingletonIterator} instance.
     */
    public static <T> SingletonIterator<T> empty() {
        return new SingletonIterator<>(null, true);
    }

    /**
     * Creates a new iterator for the singular {@code item}.
     * @param item The item to create an iterator for.
     * @return A new {@link SingletonIterator} for the given item.
     * @param <T> The type of the {@code item}.
     * @see Iterator
     */
    public static <T> SingletonIterator<T> of(T item) {
        return new SingletonIterator<>(item);
    }

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