package com.sylvona.leona.testing;

import lombok.Getter;

import java.time.Duration;

@Getter
public class TimedException extends RuntimeException {
    private final Duration executionTime;

    public TimedException(Duration duration) {
        executionTime = duration;
    }

    public TimedException(String message, Duration duration) {
        super(message);
        executionTime = duration;
    }

    public TimedException(String message, Throwable cause, Duration duration) {
        super(message, cause);
        executionTime = duration;
    }

    public TimedException(Throwable cause, Duration duration) {
        super(cause);
        executionTime = duration;
    }

    public TimedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Duration duration) {
        super(message, cause, enableSuppression, writableStackTrace);
        executionTime = duration;
    }
}
