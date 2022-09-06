package com.noah.log.annotations;

import com.noah.log.config.ThreadLocalOutputStream;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@Order(1)
public class MergeLogAspect {

    @Pointcut("@annotation(com.noah.log.annotations.MergeLog)")
    public void pointCut() {
    }

    @Around("pointCut()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {

        ThreadLocalOutputStream.start();
        Object proceed = pjp.proceed();
        String stop = ThreadLocalOutputStream.stop();
        System.out.println("--" + stop);

        return proceed;
    }

}
