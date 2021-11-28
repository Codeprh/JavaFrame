package com.noah.spring.transaction.aspect.advisor;

import org.springframework.aop.ClassFilter;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractBeanFactoryPointcutAdvisor;
import org.springframework.stereotype.Component;

/**
 * 描述:
 * 注入spiring
 *
 * @author Noah
 * @create 2021-11-25 6:17 下午
 */
@Component
public class EntityPointcutAdvisor extends AbstractBeanFactoryPointcutAdvisor {

    EntityPointcut entityPointcut = new EntityPointcut();

    public EntityPointcutAdvisor() {
        setAdvice(new EntityInterceptor());
    }

    public void setClassFilter(ClassFilter classFilter) {
        this.entityPointcut.setClassFilter(classFilter);
    }


    @Override
    public Pointcut getPointcut() {
        return entityPointcut;
    }
}
