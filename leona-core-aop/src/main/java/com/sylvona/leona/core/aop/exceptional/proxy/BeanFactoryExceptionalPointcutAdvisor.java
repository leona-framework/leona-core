package com.sylvona.leona.core.aop.exceptional.proxy;

import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractBeanFactoryPointcutAdvisor;

class BeanFactoryExceptionalPointcutAdvisor extends AbstractBeanFactoryPointcutAdvisor {
    private final ExceptionalPointcut pointcut = new ExceptionalPointcut();

    @Override
    public Pointcut getPointcut() {
        return pointcut;
    }
}
