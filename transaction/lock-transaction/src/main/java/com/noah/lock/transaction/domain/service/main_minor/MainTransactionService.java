package com.noah.lock.transaction.domain.service.main_minor;

import com.noah.lock.transaction.entity.Orders;
import com.noah.lock.transaction.service.IOrdersService;
import com.noah.lock.transaction.service.IProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class MainTransactionService {

    @Resource
    IOrdersService iOrdersService;

    @Resource
    IProductService iProductService;

    @Resource
    MinorTransactionService minorTransactionService;

    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public void doMainTransaction() {

        String name = getName();
        try {
            minorTransactionService.doMinorTransaction(name);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Orders orders = new Orders();

        orders.setName(name);
        orders.setPrice(100);

        iOrdersService.save(orders);
    }

    private String getName() {
        return "主事务" + System.currentTimeMillis();
    }

}
