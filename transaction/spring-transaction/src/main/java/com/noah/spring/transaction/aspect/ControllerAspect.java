package com.noah.spring.transaction.aspect;

import com.noah.spring.transaction.service.impl.LettuceConfigServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.NoTransactionException;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;

/**
 * 描述:
 * 控制器切面
 *
 * @author Noah
 * @create 2021-11-17 9:51 上午
 */
@Slf4j
@Aspect
@Order(0)
@Component
public class ControllerAspect {

    @Resource(name = "lettuceConfigServiceImpl")
    LettuceConfigServiceImpl lettuceConfigService;

    //@Pointcut("execution(* com.noah.spring.transaction.controller.LettuceConfigController.*(..))")
    @Pointcut("execution(* com.noah.spring.transaction.domain..*.*(..))")
    public void pointCut() {
    }

    @Pointcut("execution(* com.noah.spring.transaction.controller..*.*(..))")
    public void pointCutController() {
    }

    //@Before("pointCutController()")
    //public void beforeController() {
    //    log.info("Controller-before:{}", countConfig());
    //}

    @Before("pointCut()")
    public void before() {
        log.info("ControllerAspect-before");
    }

    @After("pointCut()")
    public void after() {
        log.info("ControllerAspect-after");
    }

    @Around("pointCut()")
    public Object aroundMethod(ProceedingJoinPoint pjp) throws Throwable {

        try {

            log.info("before-config配置总数:{}", countConfig());

            Object o = pjp.proceed();

            return o;

        } catch (Exception e) {
            //try {
            //    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            //} catch (NoTransactionException noTransactionException) {
            //    //本次的dubbo调用，无事务
            //    log.error(pjp.getSignature().toShortString(), noTransactionException);
            //}
            log.error(pjp.getSignature().toShortString(), e);
        } finally {
            log.info("finally-config配置总数:{}", countConfig());
        }

        return "200";
    }

    /**
     * 统计总数
     *
     * @return
     */
    public long countConfig() {
        return lettuceConfigService.count();
    }


}
