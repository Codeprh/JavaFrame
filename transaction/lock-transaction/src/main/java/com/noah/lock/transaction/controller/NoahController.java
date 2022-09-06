package com.noah.lock.transaction.controller;

import com.noah.lock.transaction.domain.service.ProductDomainService;
import com.noah.lock.transaction.service.IOrdersService;
import com.noah.lock.transaction.service.IProductService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import javax.annotation.Resource;

@RestController
@RequestMapping("noah")
@Slf4j
public class NoahController {

    @Resource
    ProductDomainService productDomainService;

    @Resource
    IOrdersService ordersService;

    @Resource
    IProductService productService;

    @GetMapping("/helloAsync")
    public DeferredResult<String> testDeferredResult() {

        DeferredResult<String> result = new DeferredResult<>(5000L, "i am timeout");

        ThreadLocal<String> threadLocal = new ThreadLocal<>();
        threadLocal.set("hello spring" + System.currentTimeMillis());


        new Thread(() -> {
            result.setResult("hello i am finish");
        }).start();

        return result;
    }

    @GetMapping("/product/sell")
    public String sellProduct(@RequestParam Long id) {
        productDomainService.sellProduct(id);
        return "200";
    }

    @GetMapping("/product/sell/error")
    public String sellProductError(@RequestParam Long id) {
        productDomainService.sellProductError(id);
        return "200";
    }

    @SneakyThrows
    @GetMapping("/product/add")
    public String sayHello() {
        productDomainService.saveProduct();
        return "success";

    }

}
