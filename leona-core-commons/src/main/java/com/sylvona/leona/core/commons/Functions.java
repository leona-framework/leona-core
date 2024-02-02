package com.sylvona.leona.core.commons;

import lombok.RequiredArgsConstructor;

import java.util.function.Function;

public class Functions {
    public static <T, R> Function<T, R> caching(Function<T, R> function) {
        return new CachingFunction<>(function);
    }

    @RequiredArgsConstructor
    private static class CachingFunction<T, R> implements Function<T, R> {
        private final Function<T, R> function;
        private boolean applied;
        private R result;

        @Override
        public R apply(T t) {
            if (applied) return result;
            result = function.apply(t);
            applied = true;
            return result;
        }
    }
}
