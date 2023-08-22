package com.sylvona.leona.core.commons.containers;

import com.sylvona.leona.core.commons.streams.LINQ;
import com.sylvona.leona.core.commons.streams.LINQStream;
import com.sylvona.leona.core.commons.streams.SingletonIterator;
import com.sylvona.leona.core.commons.streams.Streamable;
import jakarta.validation.constraints.NotNull;

import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * An interface representing an "either" type that holds either a successful result or an error.
 *
 * @param <OK> The type of the successful result.
 * @param <ERR> The type of the error, which is a subtype of Throwable.
 */
public interface Either<OK, ERR extends Throwable> extends Streamable<OK> {
    static <OK, ERR extends Throwable> Either<OK, ERR> ok(OK success) {
        return new EitherImpl<>(success, null);
    }

    static <OK, ERR extends Throwable> Either<OK, ERR> err(ERR throwable) {
        return new EitherImpl<>(null, throwable);
    }

    /**
     * Retrieves the successful result held by this "either" instance.
     *
     * @return The successful result.
     */
    OK result();

    /**
     * Retrieves the error held by this "either" instance.
     *
     * @return The error.
     */
    ERR error();

    /**
     * Produces a value based on the content of the "either" instance using the provided functions.
     * If the instance holds an error, the errorProducer function is applied to produce a value.
     * If the instance holds a successful result, the resultProducer function is applied.
     *
     * @param resultProducer The function to produce a value based on the successful result.
     * @param errorProducer The function to produce a value based on the error.
     * @param <T> The type of the produced value.
     * @return The produced value based on the content of the "either" instance.
     */
    default <T> T produce(Function<OK, T> resultProducer, Function<ERR, T> errorProducer) {
        return isError() ? errorProducer.apply(error()) : resultProducer.apply(result());
    }

    /**
     * Checks if the "either" instance holds an error.
     *
     * @return {@code true} if the instance holds an error, {@code false} if it holds a successful result.
     */
    default boolean isError() {
        return error() != null;
    }

    /**
     * Checks if the "either" instance holds a successful result.
     *
     * @return {@code true} if the instance holds a successful result, {@code false} if it holds an error.
     */
    default boolean isSuccess() {
        return error() == null;
    }

    default void ifSuccess(Consumer<OK> consumer) {
        if (isSuccess()) consumer.accept(result());
    }

    default void ifError(Consumer<ERR> consumer) {
        if (isError()) consumer.accept(error());
    }

    default void ifSuccessOrError(Consumer<OK> successConsumer, Consumer<ERR> errorConsumer) {
        if (isSuccess()) successConsumer.accept(result());
        else errorConsumer.accept(error());
    }

    default OK orThrows() throws ERR {
        if (isError()) throw error();
        return result();
    }

    default OK orThrows(Function<Throwable, RuntimeException> runtimeExceptionProducer) {
        if (isError()) throw runtimeExceptionProducer.apply(error());
        return result();
    }

    default OK orThrows(Supplier<RuntimeException> runtimeExceptionSupplier) {
        if (isError()) throw runtimeExceptionSupplier.get();
        return result();
    }

    default OK orThrows(RuntimeException runtimeException) {
        if (isError()) throw runtimeException;
        return result();
    }

    default OK thrown() {
        if (isError()) {
            Throwable throwable = error();
            if (throwable instanceof RuntimeException rte) throw rte;
            throw new RuntimeException(throwable);
        }
        return result();
    }

    @Override
    default LINQStream<OK> stream() {
        return LINQ.stream(this);
    }

    @Override
    default @NotNull Iterator<OK> iterator() {
        return isSuccess() ? SingletonIterator.of(result()) : SingletonIterator.empty();
    }
}
