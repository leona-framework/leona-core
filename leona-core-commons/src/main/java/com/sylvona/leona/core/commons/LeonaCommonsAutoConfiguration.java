package com.sylvona.leona.core.commons;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;

@AutoConfiguration
public class LeonaCommonsAutoConfiguration {
    private static final Logger log = LoggerFactory.getLogger(LeonaCommonsAutoConfiguration.class);

    public LeonaCommonsAutoConfiguration() {
        log.debug("Leona commons started up");
    }
}
