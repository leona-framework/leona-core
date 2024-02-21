package com.sylvona.leona.core.commons.streams;

import jakarta.validation.constraints.NotNull;

import java.util.Iterator;

/**
 * An iterator that iterates over a single item. Once the item is consumed, the iterator is considered empty.
 *
 * @param <T> The type of the item.
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

    @Override
    public boolean hasNext() {
        return !consumed;
    }

    @Override
    public T next() {
        consumed = true;
        return item;
    }
}
