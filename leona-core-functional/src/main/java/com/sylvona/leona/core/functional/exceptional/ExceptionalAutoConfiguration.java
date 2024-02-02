package com.sylvona.leona.core.functional.exceptional;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.server.ResponseStatusException;

@AutoConfiguration
@Import(ExceptionallyAspect.class)
public class ExceptionalAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnClass({ResponseStatusException.class, HttpClientErrorException.class, HttpServerErrorException.class})
    public ExceptionalHandler defaultWebExceptionalHandler() {
        return new DefaultWebExceptionalHandler();
    }

}
