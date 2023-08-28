package com.sylvona.leona.core.commons.containers;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * An interface representing a type that holds either a {@code left} value or {@code right} value.
 *
 * @param <Left> The expected {@code left} type.
 * @param <Right> The expected {@code right} type.
 */
public interface Either<Left, Right> {
    /**
     * Constructs a default implementation of {@code Either} with the given left value.
     * @param left the {@code left} value to construct the {@code Either} with.
     * @return a default implementation of {@code Either} with the left value.
     * @param <Left> the {@code left} type of the Either.
     * @param <Right> the {@code right} type of the Either.
     */
    static <Left, Right> Either<Left, Right> ofLeft(Left left) {
        return new EitherImpl<>(left, null);
    }

    /**
     * Constructs a default implementation of {@code Either} with the given right value.
     * @param right the {@code right} value to construct the {@code Either} with.
     * @return a default implementation of {@code Either} with the right value.
     * @param <Left> the {@code left} type of the Either.
     * @param <Right> the {@code right} type of the Either.
     */
    static <Left, Right> Either<Left, Right> ofRight(Right right) {
        return new EitherImpl<>(null, right);
    }

    /**
     * Retrieves the {@code left} value held by this instance.
     *
     * @return The {@code left} value.
     */
    Left left();

    /**
     * Retrieves the {@code right} value held by this instance.
     *
     * @return The {@code right} value.
     */
    Right right();

    /**
     * Produces a value based on the content of the {@code Either} instance using the provided functions.
     * If the instance has a {@code left}, the {@code leftProducer} function is applied to produce a value.
     * If the instance has a {@code right}, the {@code rightProducer} function is applied to produce a value.
     *
     * @param leftProducer The function to produce a value based on the presence of a {@code left} value.
     * @param rightProducer The function to produce a value based on the presence of a {@code right} value.
     * @param <T> The type of the produced value.
     * @return The produced value based on the content of the {@code Either} instance.
     */
    default <T> T produce(Function<Left, T> leftProducer, Function<Right, T> rightProducer) {
        return hasRight() ? rightProducer.apply(right()) : leftProducer.apply(left());
    }

    /**
     * Checks if the {@code Either} instance has a {@code right} value.
     *
     * @return {@code true} if the instance has a {@code right} error, {@code false} if it doesn't.
     */
    default boolean hasRight() {
        return right() != null;
    }

    /**
     * Checks if the {@code Either} instance has a {@code left} value.
     *
     * @return {@code true} if the instance has a {@code left} value, {@code false} if it doesn't.
     */
    default boolean hasLeft() {
        return right() == null;
    }

    /**
     * Executes a provided consumer on the contained {@code left} value if it is present.
     *
     * @param consumer The consumer to apply to the possible {@code left} value.
     */
    default void ifLeft(Consumer<Left> consumer) {
        if (hasLeft()) {
            consumer.accept(left());
        }
    }

    /**
     * Executes a provided consumer on the contained {@code right} value if it is present.
     *
     * @param consumer The consumer to apply to the possible {@code right} value.
     */
    default void ifRight(Consumer<Right> consumer) {
        if (hasRight()) {
            consumer.accept(right());
        }
    }

    /**
     * Executes a specified consumer based on the presence of a {@code left} or {@code right} value.
     * If a {@code left} value exists, the {@code leftConsumer} is executed.
     * If a {@code right} value exists, the {@code rightConsumer} is executed.
     *
     * @param leftConsumer  The consumer to be executed if the {@code left} value exists.
     * @param rightConsumer The consumer to be executed if the {@code right} value exists.
     */
    default void ifLeftOrRight(Consumer<Left> leftConsumer, Consumer<Right> rightConsumer) {
        if (hasLeft()) {
            leftConsumer.accept(left());
        } else {
            rightConsumer.accept(right());
        }
    }
}
