package com.noah.spring.transaction.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;

/**
 * 描述:
 * 子业务配置业务
 *
 * @author Noah
 * @create 2021-11-18 3:59 下午
 */
@Service
@Slf4j
public class SubLettuceConfigServiceImpl {


    @Resource(name = "lettuceConfigServiceImpl")
    LettuceConfigServiceImpl configService;

    /**
     * 子保存，并且抛出异常
     *
     * @param type
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void subThrow(Integer type) {

        try {
            configService.save(LettuceConfigServiceImpl.generatorConfig(type));
            if (type == 100) {
                throw new RuntimeException("系统异常");
            }
        } catch (Exception e) {
            log.error("sub error", e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
    }
}
