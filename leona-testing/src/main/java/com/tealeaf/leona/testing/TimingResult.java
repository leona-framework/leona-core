package org.lyora.leona.testing;

import java.time.Duration;

public record TimingResult<T>(T result, Duration executionTime) {
}
