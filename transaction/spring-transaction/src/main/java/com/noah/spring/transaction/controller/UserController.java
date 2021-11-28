package com.noah.spring.transaction.controller;

import com.noah.spring.transaction.domain.AopDomain;
import com.noah.spring.transaction.entity.AopEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 描述:
 * 用户控制器层
 *
 * @author Noah
 * @create 2021-11-15 5:49 下午
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    AopDomain aopDomain;

    @Resource
    AopEntity aopEntity;

    /**
     * 测试事务
     *
     * @return
     */
    @GetMapping("/test")
    public Integer test() {
        aopDomain.aopTransactional();
        return 200;
    }

    /**
     * 测试继承，无法切到继承类
     *
     * @return
     */
    @GetMapping("/testAop")
    public Integer testAop() {
        aopEntity.subEntityService();
        return 200;
    }

    /**
     * 直接调用到entity里面的方法
     *
     * @return
     */
    @GetMapping("/testE")
    public Integer testE() {
        aopEntity.testAopEntity();
        return 200;
    }



}
