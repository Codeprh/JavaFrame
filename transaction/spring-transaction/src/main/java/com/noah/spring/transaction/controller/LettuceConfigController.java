package com.noah.spring.transaction.controller;


import com.noah.spring.transaction.service.AopService;
import com.noah.spring.transaction.service.impl.JPALettuceConfigServiceImpl;
import com.noah.spring.transaction.service.impl.LettuceConfigServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * KV配置表 前端控制器
 * </p>
 *
 * @author noah
 * @since 2021-11-15
 */
@RestController
@RequestMapping("/lettuce")
@Slf4j
public class LettuceConfigController {

    @Resource(name = "lettuceConfigServiceImpl")
    LettuceConfigServiceImpl configService;

    @Resource(name = "jpaLettuceConfigServiceImpl")
    JPALettuceConfigServiceImpl jpaLettuceConfigService;

    @Resource
    AopService aopService;

    /**
     * 事务回滚失效：1、私有，2、自身调用
     *
     * @param type
     */
    @GetMapping("/wrong")
    public String transactionWrong(@RequestParam(defaultValue = "10") Integer type) {
        configService.saveWrong(type);
        return "200";
    }

    /**
     * 事务生效了，但是不一定回滚
     *
     * @param type
     * @return
     */
    @GetMapping("/wrongTryException")
    public String transactionTryException(@RequestParam(defaultValue = "10") Integer type) {
        configService.tryException(type);
        return "200";
    }

    /**
     * 事务正确生效
     *
     * @param type
     * @return
     */
    @GetMapping("/right")
    public String transactionRight(@RequestParam(defaultValue = "10") Integer type) {
        try {
            configService.right(type);
        } catch (Exception e) {
            log.error("error", e);
        }
        return "200";
    }

    /**
     * 事务正确生效
     *
     * @param type
     * @return
     */
    @GetMapping("/rightNoTry")
    public String rightNoTry(@RequestParam(defaultValue = "10") Integer type) {
        configService.right(type);
        return "200";
    }

    /**
     * 事务正确生效,jpa方式
     *
     * @param type
     * @return
     */
    @GetMapping("/rightJpa")
    public String transactionRightJpa(@RequestParam(defaultValue = "10") Integer type) {
        try {
            jpaLettuceConfigService.right(type);
        } catch (Exception e) {
            log.error("error", e);
        }
        return "200";
    }

    /**
     * 事务生效了，但是不一定回滚
     *
     * @param type
     * @return
     */
    @GetMapping("/tryExceptionRight")
    public String tryExceptionRight(@RequestParam(defaultValue = "10") Integer type) {
        configService.tryExceptionRight(type);
        return "200";
    }

    /**
     * 事务的传播机制，Participating in existing transaction
     * 事务默认传播机制:PROPAGATION_REQUIRED
     *
     * @return
     */
    @GetMapping("propagate")
    public String transactionPropagate(@RequestParam(defaultValue = "10") Integer type) {

        //PROPAGATION_REQUIRED,ISOLATION_DEFAULT
        configService.propagate(type);
        return "200";
    }

    /**
     * 事务正确生效,jpa方式
     *
     * @param type
     * @return
     */
    @GetMapping("/aop")
    public String aop(@RequestParam(defaultValue = "10") Integer type) {
        try {
            aopService.testAop(type);
        } catch (Exception e) {
            log.error("error", e);
        }
        return "200";
    }

}
