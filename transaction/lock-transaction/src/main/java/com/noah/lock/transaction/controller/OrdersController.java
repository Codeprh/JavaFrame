package com.noah.lock.transaction.controller;


import com.noah.lock.transaction.domain.service.main_minor.MainTransactionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author noah
 * @since 2022-03-05
 */
@RestController
@RequestMapping("/orders")
public class OrdersController {

    @Resource
    MainTransactionService mainTransactionService;

    @GetMapping("sell")
    public String sell() {
        mainTransactionService.doMainTransaction();
        return "success";
    }

    @GetMapping("hello")
    public String hello() {
        return "hello world";
    }

}
