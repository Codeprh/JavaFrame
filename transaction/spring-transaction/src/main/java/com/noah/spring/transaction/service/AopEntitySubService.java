package com.noah.spring.transaction.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;

/**
 * 描述:
 * 业务测试
 *
 * @author Noah
 * @create 2021-11-25 3:41 下午
 */
@Service
@Slf4j
public class AopEntitySubService {

    public void subEntityService() {
        log.info("subEntityService,subEntityService");
    }
}
