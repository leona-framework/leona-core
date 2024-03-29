package com.sylvona.leona.core.functional.exceptional;

import jakarta.annotation.Nullable;
import lombok.Getter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.function.Consumer;

@Getter
class ErrorCompositeImpl implements ErrorComposite {
    private final HttpHeaders headers;
    private String errorMessage;
    private HttpStatusCode statusCode;
    private Object body;

    ErrorCompositeImpl(Throwable error) {
        if (error instanceof ErrorResponseException errorResponseException) {
            headers = errorResponseException.getHeaders();
            errorMessage = errorResponseException.getMessage();
            statusCode = errorResponseException.getStatusCode();
            body = errorResponseException.getBody();
        } else if (error instanceof HttpStatusCodeException statusCodeException) {
            headers = statusCodeException.getResponseHeaders();
            errorMessage = statusCodeException.getMessage();
            statusCode = statusCodeException.getStatusCode();
            body = statusCodeException.getResponseBodyAsByteArray();
        } else {
            headers = new HttpHeaders();
            statusCode = HttpStatusCode.valueOf(500);
        }
    }

    @Override
    public ErrorResponseSpec body(Object body) {
        this.body = body;
        return this;
    }

    @Override
    public ErrorResponseSpec errorMessage(@Nullable String message) {
        this.errorMessage = message;
        return this;
    }

    @Override
    public ErrorResponseSpec headers(Consumer<HttpHeaders> httpHeadersConsumer) {
        httpHeadersConsumer.accept(headers);
        return this;
    }

    @Override
    public ErrorResponseSpec status(HttpStatusCode statusCode) {
        this.statusCode = statusCode;
        return this;
    }
}
