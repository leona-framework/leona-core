package com.sylvona.leona.core.aop.exceptional.proxy;

import com.sylvona.leona.core.aop.exceptional.AnnotatedMethodInvoker;
import com.sylvona.leona.core.aop.exceptional.DefaultWebExceptionalHandler;
import com.sylvona.leona.core.aop.exceptional.ExceptionalHandler;
import org.springframework.aop.support.AbstractBeanFactoryPointcutAdvisor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Role;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.server.ResponseStatusException;

@AutoConfiguration
public class ProxyExceptionalConfiguration {
    @Bean
    AbstractBeanFactoryPointcutAdvisor exceptionalPointcutAdvisor(ExceptionalMethodInterceptor exceptionalMethodInterceptor) {
        BeanFactoryExceptionalPointcutAdvisor advisor = new BeanFactoryExceptionalPointcutAdvisor();
        advisor.setOrder(0);
        advisor.setAdvice(exceptionalMethodInterceptor);
        advisor.setAdviceBeanName("exceptionalMethodInterceptor");
        return advisor;
    }

    @Bean
    ExceptionalMethodInterceptor exceptionalMethodInterceptor(AnnotatedMethodInvoker annotatedMethodInvoker) {
        return new ExceptionalMethodInterceptor(annotatedMethodInvoker);
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public AnnotatedMethodInvoker annotatedMethodInvoker(ExceptionalHandler exceptionalHandler) {
        return new AnnotatedMethodInvoker(exceptionalHandler);
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnClass({ResponseStatusException.class, HttpClientErrorException.class, HttpServerErrorException.class})
    public ExceptionalHandler defaultWebExceptionalHandler() {
        return new DefaultWebExceptionalHandler();
    }
}
