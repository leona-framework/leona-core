package com.sylvona.leona.core.commons.ttl;

import org.springframework.context.annotation.Import;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Enables TTL (Time-To-Live) capabilities for the application. These capabilities utilize Spring's AOP system to create and manage TTL objects for the application.
 */
@Retention(RetentionPolicy.RUNTIME)
@Import(LeonaCommonsTTLAutoConfiguration.class)
public @interface EnableTTL {
}
