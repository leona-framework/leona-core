package com.sylvona.leona.core.commons;

import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Utility class containing various function-related operations.
 */
public class Functions {

    /**
     * Creates a caching version of the given function. The caching function stores the result of the initial
     * function call and returns the same result when invoked again with the same input.
     *
     * @param function The function to be cached.
     * @param <T>      The type of the input to the function.
     * @param <R>      The type of the result of the function.
     * @return A caching function.
     */
    public static <T, R> Function<T, R> caching(Function<T, R> function) {
        return new CachingFunction<>(function);
    }

    /**
     * Private inner class representing a caching function decorator.
     *
     * @param <T> The type of the input to the function.
     * @param <R> The type of the result of the function.
     */
    @RequiredArgsConstructor
    private static class CachingFunction<T, R> implements Function<T, R> {
        private final Map<T, R> inputToResultCache = new HashMap<>(1);
        private final Function<T, R> function;

        /**
         * Applies the caching logic to the wrapped function.
         *
         * @param t The input to the function.
         * @return The result of the function.
         */
        @Override
        public R apply(T t) {
            return inputToResultCache.computeIfAbsent(t, function);
        }
    }
}
