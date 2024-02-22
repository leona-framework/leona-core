package com.sylvona.leona.core.aop.exceptional;

import jakarta.annotation.Nullable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import java.util.function.Consumer;

public interface ErrorResponseSpec {
    ErrorResponseSpec body(Object body);

    ErrorResponseSpec errorMessage(@Nullable String message);

    ErrorResponseSpec headers(Consumer<HttpHeaders> httpHeadersConsumer);

    ErrorResponseSpec status(HttpStatusCode statusCode);

    default ErrorResponseSpec status(HttpStatus status) {
        return status(HttpStatusCode.valueOf(status.value()));
    }
}
