package com.sylvona.leona.core.aop.exceptional;

public interface ExceptionalHandler {
    ErrorComposite createComposite(Throwable source);
    ShortCircuitingResponseException produceError(StatusCodeErrorView statusCodeErrorView);
}