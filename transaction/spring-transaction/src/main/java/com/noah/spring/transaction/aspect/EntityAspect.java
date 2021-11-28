package com.noah.spring.transaction.aspect;

import com.noah.spring.transaction.entity.AopEntity;
import com.noah.spring.transaction.service.AopEntitySubService;
import com.noah.spring.transaction.service.impl.LettuceConfigServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.DeclareParents;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

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
public class EntityAspect {

    @Resource(name = "lettuceConfigServiceImpl")
    LettuceConfigServiceImpl lettuceConfigService;

    @Pointcut("execution(* com.noah.spring.transaction.entity..*+.*(..))")
    public void pointCut() {
    }

    @Around("pointCut()")
    public Object aroundMethod(ProceedingJoinPoint pjp) throws Throwable {

        try {

            log.info("entity-config配置总数:{}", countConfig());

            Object o = pjp.proceed();

            return o;

        } catch (Exception e) {
            log.error(pjp.getSignature().toShortString(), e);
        } finally {
            log.info("entity-config配置总数:{}", countConfig());
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

    //@DeclareParents(value = "com.noah.spring.transaction.service.AopEntitySubService+",defaultImpl = AopEntity.class)
    //public AopEntitySubService aopEntitySubService;


}
