package com.noah.lock.transaction.controller;

import com.noah.lock.transaction.domain.service.ProductDomainService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("noah")
public class NoahController {

    @Resource
    ProductDomainService productDomainService;

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

}
