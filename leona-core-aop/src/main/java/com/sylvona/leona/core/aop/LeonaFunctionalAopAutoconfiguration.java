package com.sylvona.leona.core.aop;

import com.sylvona.leona.core.aop.exceptional.ExceptionalAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Import;

@AutoConfiguration
@Import(ExceptionalAutoConfiguration.class)
public class LeonaFunctionalAopAutoconfiguration {
}
