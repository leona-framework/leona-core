package com.sylvona.leona.core.functional.exceptional;

import com.sylvona.leona.core.commons.containers.Either;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Getter
@Accessors(fluent = true)
@AllArgsConstructor
public final class Exceptional<T> implements Either<T, Throwable> {
    private final T left;
    private final Throwable right;

    @Getter(AccessLevel.NONE)
    ExceptionalHandler attachedHandler;

    public static <T> Exceptional<T> ok(T result) {
        return new Exceptional<>(result, null, null);
    }

    public static <T> Exceptional<T> right(Throwable error) {
        return new Exceptional<>(null, error, null);
    }

    @SuppressWarnings("unchecked")
    public <OK2, E2 extends Either<OK2, Throwable>> E2 map(Function<T, OK2> mapper) {
        return hasRight() ? (E2) new Exceptional<>(null, right, attachedHandler) : (E2) new Exceptional<>(mapper.apply(left), right, attachedHandler);
    }

    public T respond() {
        if (hasLeft()) return left;
        throw attachedHandler.produceError(attachedHandler.createComposite(right));
    }

    public T respond(Consumer<ErrorResponseSpec> exceptionBuilder) {
        if (hasLeft()) return left;
        ErrorComposite errorComposite = attachedHandler.createComposite(right);
        exceptionBuilder.accept(errorComposite);
        throw attachedHandler.produceError(errorComposite);
    }

    public T respond(ExceptionBuilder exceptionBuilder) {
        if (hasLeft()) return left;
        ErrorComposite errorComposite = attachedHandler.createComposite(right);
        exceptionBuilder.accept(right, errorComposite);
        throw attachedHandler.produceError(errorComposite);
    }

    public T orThrows() throws Throwable {
        if (hasRight()) throw right();
        return left();
    }

    public T orThrows(Function<Throwable, RuntimeException> runtimeExceptionProducer) {
        if (hasRight()) throw runtimeExceptionProducer.apply(right());
        return left();
    }

    public T orThrows(Supplier<RuntimeException> runtimeExceptionSupplier) {
        if (hasRight()) throw runtimeExceptionSupplier.get();
        return left();
    }

    public T orThrows(RuntimeException runtimeException) {
        if (hasRight()) throw runtimeException;
        return left();
    }

    public T thrown() {
        if (hasRight()) {
            Throwable throwable = right();
            if (throwable instanceof RuntimeException rte) throw rte;
            throw new RuntimeException(throwable);
        }
        return left();
    }
}
