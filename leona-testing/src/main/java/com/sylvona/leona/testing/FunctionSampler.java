package com.sylvona.leona.testing;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
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

        @Override
        public String toString() {
            return toString(TimeUnit.MILLISECONDS);
        }

        public String toString(TimeUnit timeUnit) {
            String suffix = getTimeUnitSuffix(timeUnit);
            return "Results { Total = %s%s |-| Average = %s%s |-| Max = %s%s |-| Min = %s%s }".formatted(
                    convertDuration(totalExecutionTime, timeUnit), suffix,
                    convertDuration(averageExecutionTime(), timeUnit), suffix,
                    convertDuration(maxExecutionTime, timeUnit), suffix,
                    convertDuration(minExecutionTime, timeUnit), suffix
            );
        }

        private static float convertDuration(Duration duration, TimeUnit timeUnit) {
            return switch (timeUnit) {
                case NANOSECONDS -> duration.toNanos();
                case MICROSECONDS -> duration.toNanos() / 1000f;
                case MILLISECONDS -> duration.toNanos() / 1_000_000f;
                case SECONDS -> duration.toMillis() / 1000f;
                case MINUTES -> duration.toMillis() / 60_000f;
                case HOURS -> duration.toSeconds() / 3600f;
                case DAYS -> duration.toMinutes() / 1440f;
            };
        }

        private static String getTimeUnitSuffix(TimeUnit timeUnit) {
            return switch (timeUnit) {
                case NANOSECONDS -> "ns";
                case MICROSECONDS -> "mis";
                case MILLISECONDS -> "ms";
                case SECONDS -> "s";
                case MINUTES -> "m";
                case HOURS -> "h";
                case DAYS -> "d";
            };
        }
    }
}
