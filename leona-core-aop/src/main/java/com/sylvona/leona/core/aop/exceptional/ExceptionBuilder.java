package com.sylvona.leona.core.aop.exceptional;

import java.util.function.BiConsumer;

@FunctionalInterface
public interface ExceptionBuilder extends BiConsumer<Throwable, ErrorResponseSpec> {
}
