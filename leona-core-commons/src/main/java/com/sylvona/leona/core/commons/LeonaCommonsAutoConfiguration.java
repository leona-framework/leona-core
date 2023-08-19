package com.sylvona.leona.core.commons;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;

@Slf4j
@AutoConfiguration
public class LeonaCommonsAutoConfiguration {
    public LeonaCommonsAutoConfiguration() {
        log.info("Leona commons started up");
    }
}
