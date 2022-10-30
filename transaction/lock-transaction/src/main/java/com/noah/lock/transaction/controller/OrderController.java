package com.noah.lock.transaction.controller;


import com.noah.lock.transaction.domain.service.main_minor.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 订单表 前端控制器
 * </p>
 *
 * @author noah
 * @since 2022-10-28
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Resource
    OrderService orderService;

    @GetMapping("save")
    public String save() {
        orderService.doOrder();
        return "success";
    }
}
