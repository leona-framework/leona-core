package com.sylvona.leona.core.aop.exceptional;

import org.springframework.http.ResponseEntity;

import java.util.function.Function;

public class WebExceptional<T> extends Exceptional<T> {
    public WebExceptional(T left, Throwable right, ExceptionalHandler attachedHandler) {
        super(left, right, attachedHandler);
    }

    public static <T> WebExceptional<T> ok(T item) {
        return new WebExceptional<>(item, null, null);
    }

    public static <T> WebExceptional<T> err(Throwable exception) {
        return new WebExceptional<>(null, exception, null);
    }

    public ResponseEntity<T> response() {
        if (hasLeft()) return ResponseEntity.ok(left());
        ErrorComposite errorComposite = attachedHandler.createComposite(right());
        throw ShortCircuitedException.wrap(attachedHandler.produceException(errorComposite));
    }

    public ResponseEntity<?> response(Function<ErrorResponseSpec, ResponseEntity<?>> transform) {
        if (hasLeft()) return ResponseEntity.ok(left());
        ErrorComposite errorComposite = attachedHandler.createComposite(right());
        return transform.apply(errorComposite);
    }

    public ResponseEntity<?> response(Function<T, ?> leftTransform, Function<ErrorResponseSpec, ResponseEntity<?>> rightTransform) {
        if (hasLeft()) return ResponseEntity.ok(leftTransform.apply(left()));
        ErrorComposite errorComposite = attachedHandler.createComposite(right());
        return rightTransform.apply(errorComposite);
    }
}
