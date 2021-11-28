package com.noah.spring.transaction.entity;

import com.noah.spring.transaction.service.AopEntitySubService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;

/**
 * 描述:
 * aop实体测试
 *
 * @author Noah
 * @create 2021-11-25 1:08 下午
 */
@Service
@Slf4j
public class AopEntity extends AopEntitySubService {

    public Integer testAopEntity() {
        log.info("AopEntity,AopEntity");
        return 200;
    }
}
