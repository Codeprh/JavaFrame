package com.noah.spring.transaction.aspect.advisor;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.io.Serializable;

/**
 * 描述:
 * 拦截器
 *
 * @author Noah
 * @create 2021-11-25 6:14 下午
 */
@Slf4j
public class EntityInterceptor implements MethodInterceptor, Serializable {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        // 增强处理
        //todo:实现更复杂逻辑，用spring业务
        Object proceed = null;
        try {

            proceed = invocation.proceed();

        } catch (Exception e) {
            log.error("EntityInterceptor", e);
        } finally {
            log.info("EntityInterceptor-finish");
        }

        return proceed;
    }
}
