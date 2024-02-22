package com.sylvona.leona.core.commons.containers;

import jakarta.validation.constraints.NotNull;

/**
 * Represents a tuple containing an index and a value, typically used for enumeration purposes.
 *
 * @param <T> The type of the value.
 */
public class Enumerated<T> extends Tuple<Integer, T> {
    /**
     * Constructs an Enumerated tuple with the specified index and value.
     *
     * @param index The index of the tuple.
     * @param value The value of the tuple.
     */
    public Enumerated(@NotNull Integer index, @NotNull T value) {
        super(index, value);
    }

    /**
     * Gets the index of the tuple.
     *
     * @return The index of the tuple.
     */
    public int getIndex() {
        return item1();
    }

    /**
     * Gets the value of the tuple.
     *
     * @return The value of the tuple.
     */
    public T getValue() {
        return item2();
    }
}
