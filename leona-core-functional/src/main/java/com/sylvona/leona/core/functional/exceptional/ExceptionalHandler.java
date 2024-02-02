package com.sylvona.leona.core.functional.exceptional;

public interface ExceptionalHandler {
    ErrorComposite createComposite(Throwable source);
    ShortCircuitingResponseException produceError(StatusCodeErrorView statusCodeErrorView);
}
