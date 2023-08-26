package com.sylvona.leona.core.commons.containers;

import com.sylvona.leona.core.commons.streams.LINQ;
import com.sylvona.leona.core.commons.streams.LINQStream;
import com.sylvona.leona.core.commons.streams.SingletonIterator;
import com.sylvona.leona.core.commons.streams.Streamable;
import jakarta.validation.constraints.NotNull;

import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * An interface representing an "either" type that holds either a successful result or an error.
 *
 * @param <Left> The type of the successful result.
 * @param <Right> The type of the error, which is a subtype of Throwable.
 */
public interface Either<Left, Right> extends Streamable<Tuple<Left, Right>> {
    static <Left, Right> Either<Left, Right> ofLeft(Left left) {
        return new EitherImpl<>(left, null);
    }

    static <Left, Right> Either<Left, Right> ofRight(Right right) {
        return new EitherImpl<>(null, right);
    }

    /**
     * Retrieves the successful result held by this "either" instance.
     *
     * @return The successful result.
     */
    Left left();

    /**
     * Retrieves the error held by this "either" instance.
     *
     * @return The error.
     */
    Right right();

    /**
     * Produces a value based on the content of the "either" instance using the provided functions.
     * If the instance holds an error, the rightProducer function is applied to produce a value.
     * If the instance holds a successful result, the leftProducer function is applied.
     *
     * @param leftProducer The function to produce a value based on the successful result.
     * @param rightProducer The function to produce a value based on the error.
     * @param <T> The type of the produced value.
     * @return The produced value based on the content of the "either" instance.
     */
    default <T> T produce(Function<Left, T> leftProducer, Function<Right, T> rightProducer) {
        return hasRight() ? rightProducer.apply(right()) : leftProducer.apply(left());
    }

    /**
     * Checks if the "either" instance holds an error.
     *
     * @return {@code true} if the instance holds an error, {@code false} if it holds a successful result.
     */
    default boolean hasRight() {
        return right() != null;
    }

    /**
     * Checks if the "either" instance holds a successful result.
     *
     * @return {@code true} if the instance holds a successful result, {@code false} if it holds an error.
     */
    default boolean hasLeft() {
        return right() == null;
    }

    default void ifLeft(Consumer<Left> consumer) {
        if (hasLeft()) consumer.accept(left());
    }

    default void ifRight(Consumer<Right> consumer) {
        if (hasRight()) consumer.accept(right());
    }

    default void ifLeftOrRight(Consumer<Left> successConsumer, Consumer<Right> errorConsumer) {
        if (hasLeft()) successConsumer.accept(left());
        else errorConsumer.accept(right());
    }

    @Override
    default LINQStream<Tuple<Left, Right>> stream() {
        return LINQ.stream(this);
    }

    @Override
    default @NotNull Iterator<Tuple<Left, Right>> iterator() {
        return hasLeft() ? SingletonIterator.of(Tuple.of(left(), right())) : SingletonIterator.empty();
    }
}
