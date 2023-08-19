package com.sylvona.leona.testing;

import java.time.Duration;
import java.util.function.Supplier;

public class FunctionSampler {
    private final long samples;

    public FunctionSampler(long samples) {
        this.samples = samples;
    }

    public Result time(Runnable runnable, long samples) {
        long totalExecutionTime = 0;
        long minExecutionTime = 0;
        long maxExecutionTime = 0;

        for (int i = 0; i < samples; i++) {
            Duration duration = FunctionTimer.timeRunnable(runnable);
            long executionNanos = duration.toNanos();
            totalExecutionTime += executionNanos;
            minExecutionTime = Math.min(minExecutionTime, executionNanos);
            maxExecutionTime = Math.max(maxExecutionTime, executionNanos);
        }

        return new Result(Duration.ofNanos(totalExecutionTime), Duration.ofNanos(minExecutionTime), Duration.ofNanos(maxExecutionTime), samples);
    }

    public Result time(Runnable runnable) {
        return time(runnable, samples);
    }

    public Result time(Supplier<?> supplier, long samples) {
        return time(() -> { supplier.get(); }, samples);
    }

    public Result time(Supplier<?> supplier) {
        return time(() -> { supplier.get(); });
    }

    public record Result(Duration totalExecutionTime, Duration minExecutionTime, Duration maxExecutionTime, long samples) {
        public Duration averageExecutionTime() {
            return Duration.ofNanos(totalExecutionTime.toNanos() / samples);
        }
    }
}
