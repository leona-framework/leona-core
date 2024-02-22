package com.sylvona.leona.core.commons.containers;

import com.google.common.collect.Iterators;
import com.sylvona.leona.core.commons.streams.Streamable;
import jakarta.validation.constraints.NotNull;

import java.util.Iterator;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * A record representing a tuple of two elements, each with distinct types.
 * This record provides utility methods for appending elements, concatenating tuples,
 * mapping elements, and converting the tuple to an array.
 *
 * @param <T1> The type of the first element.
 * @param <T2> The type of the second element.
 *
 * @author Evan Cowin
 * @since 0.0.1
 */
public class Tuple<T1, T2> implements Streamable<Object> {
    private final @NotNull T1 item1;
    private final @NotNull T2 item2;

    /**
     * Creates a new instance of {@code Tuple} with the specified items.
     *
     * @param item1 The first item.
     * @param item2 The second item.
     * @param <T1>  The type of the first item.
     * @param <T2>  The type of the second item.
     * @return A new {@code Tuple} containing the specified items.
     * @throws NullPointerException If either {@code item1} or {@code item2} is {@code null}.
     */
    public static <T1, T2> Tuple<T1, T2> of(@NotNull T1 item1, @NotNull T2 item2) {
        return new Tuple<>(item1, item2);
    }

    /**
     * Constructs a new {@code Tuple} instance with the provided items.
     *
     * @param item1 The first element of the tuple.
     * @param item2 The second element of the tuple.
     */
    public Tuple(@NotNull T1 item1, @NotNull T2 item2) {
        Objects.requireNonNull(item1, "item1");
        Objects.requireNonNull(item2, "item2");
        this.item1 = item1;
        this.item2 = item2;
    }

    /**
     * Appends a third element to the tuple, creating a new {@code Triple} instance.
     *
     * @param item3 The third element to append.
     * @param <T3>  The type of the third element.
     * @return A new triple with the appended third element.
     */
    public <T3> Triple<T1, T2, T3> append(T3 item3) {
        return new Triple<>(item1, item2, item3);
    }

    /**
     * Concatenates the tuple with a third element, creating a new tuple containing the original tuple and the third element.
     *
     * @param item3 The third element to concatenate.
     * @param <T3>  The type of the third element.
     * @return A new tuple containing the original tuple and the concatenated third element.
     */
    public <T3> Tuple<Tuple<T1, T2>, T3> concat(T3 item3) {
        return new Tuple<>(this, item3);
    }

    /**
     * Maps the first element of the tuple using the provided mapper function,
     * creating a new tuple with the mapped first element.
     *
     * @param mapper The function to map the first element.
     * @param <R>    The type of the mapped element.
     * @return A new tuple with the mapped first element.
     */
    public <R> Tuple<R, T2> mapT1(Function<T1, R> mapper) {
        return new Tuple<>(mapper.apply(item1), item2);
    }

    /**
     * Maps the second element of the tuple using the provided mapper function,
     * creating a new tuple with the mapped second element.
     *
     * @param mapper The function to map the second element.
     * @param <R>    The type of the mapped element.
     * @return A new tuple with the mapped second element.
     */
    public <R> Tuple<T1, R> mapT2(Function<T2, R> mapper) {
        return new Tuple<>(item1, mapper.apply(item2));
    }

    /**
     * Applies the first and second elements of the tuple to a composer function, producing and returning singular result.
     *
     * @param composer The {@link BiFunction} receiving the elements of the tuple.
     * @param <R>      The type of the composed object.
     * @return A newly composed object
     */
    public <R> R compose(BiFunction<? super T1, ? super T2, R> composer) {
        return composer.apply(item1, item2);
    }

    /**
     * Converts the tuple to an array containing both elements.
     *
     * @return An array containing both elements of the tuple.
     */
    public Object[] toArray() {
        return new Object[]{item1, item2};
    }

    /**
     * Returns an iterator over both elements of the tuple as an array.
     *
     * @return An iterator over both elements of the tuple.
     */
    @Override
    public Iterator<Object> iterator() {
        return Iterators.forArray(toArray());
    }

    public @NotNull T1 item1() {
        return item1;
    }

    public @NotNull T2 item2() {
        return item2;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Tuple) obj;
        return Objects.equals(this.item1, that.item1) &&
                Objects.equals(this.item2, that.item2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(item1, item2);
    }

    @Override
    public String toString() {
        return "Tuple[" +
                "item1=" + item1 + ", " +
                "item2=" + item2 + ']';
    }

}
