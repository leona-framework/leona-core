package com.sylvona.leona.core.utils;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
class LeonaUtilsAutoConfiguration {
    @Bean
    public BeanFirstClassConstructor beanFirstClassConstructor(ApplicationContext applicationContext) {
        return new BeanFirstClassConstructor(applicationContext);
    }
}
