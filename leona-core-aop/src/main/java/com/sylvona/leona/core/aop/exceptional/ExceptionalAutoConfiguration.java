package com.sylvona.leona.core.aop.exceptional;

import com.sylvona.leona.core.aop.annotations.EnableExceptionally;
import com.sylvona.leona.core.aop.exceptional.proxy.ProxyExceptionalConfiguration;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.AdviceModeImportSelector;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@EnableAspectJAutoProxy
@Order(Ordered.HIGHEST_PRECEDENCE)
@AutoConfiguration
public class ExceptionalAutoConfiguration extends AdviceModeImportSelector<EnableExceptionally> {
    @Override
    protected String[] selectImports(AdviceMode adviceMode) {
        return new String[] { ProxyExceptionalConfiguration.class.getName() };
    }

}
