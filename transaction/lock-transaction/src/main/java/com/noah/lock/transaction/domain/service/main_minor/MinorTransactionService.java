package com.noah.lock.transaction.domain.service.main_minor;

import com.noah.lock.transaction.entity.Product;
import com.noah.lock.transaction.service.IOrdersService;
import com.noah.lock.transaction.service.IProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class MinorTransactionService {

    @Resource
    IOrdersService iOrdersService;

    @Resource
    IProductService iProductService;

    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRES_NEW)
    public void doMinorTransaction(String name) {

        Product product = new Product();


        product.setStock(0L);
        product.setName("orders=" + name);

        iProductService.save(product);

        int a = 1 / 0;

    }
}
