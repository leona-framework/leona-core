package com.sylvona.leona.core.commons.ttl;

import org.springframework.context.annotation.Import;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Import(LeonaCommonsTTLAutoConfiguration.class)
public @interface EnableTTL {
}
