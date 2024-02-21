package com.sylvona.leona.core.commons.streams;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Iterator;

/**
 * An iterator that iterates over a single item. Once the item is consumed, the iterator is considered empty.
 *
 * @param <T> The type of the item.
 *
 * @see Iterator
 *
 * @author Evan Cowin
 * @since 0.0.1
 */
@RequiredArgsConstructor(staticName = "of")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SingletonIterator<T> implements Iterator<T>, Iterable<T> {
    private final T item;
    private boolean consumed;

    /**
     * Creates an empty instance of {@link SingletonIterator}.
     *
     * @param <T> The type of the item.
     * @return An empty {@link SingletonIterator} instance.
     */
    public static <T> SingletonIterator<T> empty() {
        return new SingletonIterator<>(null, true);
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
