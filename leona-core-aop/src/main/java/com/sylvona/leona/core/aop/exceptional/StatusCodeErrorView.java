package com.sylvona.leona.core.aop.exceptional;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;

public interface StatusCodeErrorView {
    Object getBody();
    String getErrorMessage();
    HttpHeaders getHeaders();
    HttpStatusCode getStatusCode();
}
