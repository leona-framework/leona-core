package com.sylvona.leona.core.functional.exceptional;

import com.sylvona.leona.core.commons.containers.Either;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.function.Consumer;
import java.util.function.Function;

@Getter
@Accessors(fluent = true)
@AllArgsConstructor
public class Exceptional<T> implements Either<T, Throwable> {
    private final T result;
    private final Throwable error;

    @Getter(AccessLevel.NONE)
    ExceptionalHandler attachedHandler;

    public static <T> Exceptional<T> ok(T result) {
        return new Exceptional<>(result, null, null);
    }

    public static <T> Exceptional<T> error(Throwable error) {
        return new Exceptional<>(null, error, null);
    }

    @SuppressWarnings("unchecked")
    public <OK2, E2 extends Either<OK2, Throwable>> E2 map(Function<T, OK2> mapper) {
        return isError() ? (E2) new Exceptional<>(null, error, attachedHandler) : (E2) new Exceptional<>(mapper.apply(result), error, attachedHandler);
    }

    public T respond() {
        if (isSuccess()) return result;
        throw attachedHandler.produceError(attachedHandler.createComposite(error));
    }

    public T respond(Consumer<ErrorResponseSpec> exceptionBuilder) {
        if (isSuccess()) return result;
        ErrorComposite errorComposite = attachedHandler.createComposite(error);
        exceptionBuilder.accept(errorComposite);
        throw attachedHandler.produceError(errorComposite);
    }

    public T respond(ExceptionBuilder exceptionBuilder) {
        if (isSuccess()) return result;
        ErrorComposite errorComposite = attachedHandler.createComposite(error);
        exceptionBuilder.accept(error, errorComposite);
        throw attachedHandler.produceError(errorComposite);
    }
}
