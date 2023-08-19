package org.lyora.leona.testing;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

public class FunctionTimer {
    private final ThreadLocal<Map<Duration, Object>> results = ThreadLocal.withInitial(HashMap::new);
    private final ThreadLocal<Object> lastResult = new ThreadLocal<>();

    public Duration runnable(Runnable runnable) {
        return timeRunnable(runnable);
    }

    public <T> TimingResult<T> supplier(Supplier<T> supplier) {
        return timeSupplier(supplier);
    }

    public Duration lazySupplier(Supplier<?> supplier) {
        long startTime = System.nanoTime();
        return createLazyResult(supplier.get(), startTime);
    }

    public <T> TimingResult<T> callable(Callable<T> callable) {
        long startTime = System.nanoTime();
        try {
            return new TimingResult<>(callable.call(), Duration.ofNanos(System.nanoTime() - startTime));
        } catch (Exception e) {
            throw new TimedException(e, Duration.ofNanos(System.nanoTime() - startTime));
        }
    }

    public Duration lazyCallable(Callable<?> callable) {
        long startTime = System.nanoTime();
        try {
            return createLazyResult(callable.call(), startTime);
        } catch (Exception e) {
            throw new TimedException(e, Duration.ofNanos(System.nanoTime() - startTime));
        }
    }

    public static Duration timeRunnable(Runnable runnable) {
        long startTime = System.nanoTime();
        runnable.run();
        return Duration.ofNanos(System.nanoTime() - startTime);
    }

    public static <T> TimingResult<T> timeSupplier(Supplier<T> supplier) {
        long startTime = System.nanoTime();
        return new TimingResult<>(supplier.get(), Duration.ofNanos(System.nanoTime() - startTime));
    }

    @SuppressWarnings("unchecked")
    public <T> T getResult(Duration duration) {
        return (T) results.get().get(duration);
    }

    @SuppressWarnings("unchecked")
    public <T> T getLastResult() {
        return (T) lastResult.get();
    }

    private Duration createLazyResult(Object result, long startTime) {
        Map<Duration, Object> localResults = results.get();
        Duration duration = Duration.ofNanos(System.nanoTime() - startTime);
        localResults.put(duration, result);
        lastResult.set(result);
        return duration;
    }
}
