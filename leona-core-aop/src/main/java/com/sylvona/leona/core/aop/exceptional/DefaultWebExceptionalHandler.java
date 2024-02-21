package com.sylvona.leona.core.aop.exceptional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.Charset;

@ConditionalOnClass({ResponseStatusException.class, HttpClientErrorException.class, HttpServerErrorException.class})
class DefaultWebExceptionalHandler implements ExceptionalHandler {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Charset defaultCharset = Charset.defaultCharset();

    @Override
    public ErrorComposite createComposite(Throwable source) {
        return new ErrorCompositeImpl(source);
    }

    @Override
    public ShortCircuitingResponseException produceError(StatusCodeErrorView statusCodeErrorView) {
        return ShortCircuitingResponseException.wrap(produceRuntimeException(statusCodeErrorView));
    }

    private RuntimeException produceRuntimeException(StatusCodeErrorView statusCodeErrorView) {
        HttpStatusCode statusCode = statusCodeErrorView.getStatusCode();

        byte[] body;
        try {
            Object genericBody = statusCodeErrorView.getBody();
            if (genericBody == null) body = null;
            else body = objectMapper.writeValueAsBytes(genericBody);
        } catch (JsonProcessingException exception) {
            throw new RuntimeException(exception);
        }

        if (statusCode.is5xxServerError()) {
            return new HttpServerErrorException(statusCode, statusCodeErrorView.getErrorMessage(), statusCodeErrorView.getHeaders(), body, defaultCharset);
        }

        return new HttpClientErrorException(statusCode, statusCodeErrorView.getErrorMessage(), statusCodeErrorView.getHeaders(), body, defaultCharset);
    }
}
