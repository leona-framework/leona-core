package com.tealeaf.leona.core.commons;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

@Import(TTLMethodAspect.class)
@AutoConfiguration
@EnableAspectJAutoProxy
public class LeonaCommonsTTLAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public TTLStore<Object> ttlStoreForTtlMethodAspects() {
        return new CdsoTTLStore<>();
    }

}
