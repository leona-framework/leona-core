package com.sylvona.leona.core.commons;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * A utility class containing various functional programming utilities.
 *
 * @author Evan Cowin
 * @since 0.0.1
 */
public class Functions {
    /**
     * Wraps a function with caching behavior, ensuring that the function is only evaluated once for each input,
     * with subsequent calls returning the cached result.
     *
     * @param function The function to be wrapped with caching behavior.
     * @param <T>      The type of the input to the function.
     * @param <R>      The type of the result of the function.
     * @return A caching function that caches the result of the input function for each input.
     */
    public static <T, R> Function<T, R> caching(Function<T, R> function) {
        return new CachingFunction<>(function);
    }

    private static class CachingFunction<T, R> implements Function<T, R> {
        private final Function<T, R> function;
        private final Map<T, R> inputOutputMap = new HashMap<>();

        public CachingFunction(Function<T, R> function) {
            this.function = function;
        }

        @Override
        public R apply(T t) {
            return inputOutputMap.computeIfAbsent(t, function);
        }
    }
}
