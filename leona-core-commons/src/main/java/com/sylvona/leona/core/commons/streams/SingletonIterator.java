package com.sylvona.leona.core.commons.streams;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Iterator;

@RequiredArgsConstructor(staticName = "of")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SingletonIterator<T> implements Iterator<T>, Iterable<T> {
    private final T item;
    private boolean consumed;

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
