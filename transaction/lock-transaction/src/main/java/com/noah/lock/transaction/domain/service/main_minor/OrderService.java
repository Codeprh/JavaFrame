package com.noah.lock.transaction.domain.service.main_minor;

import com.noah.lock.transaction.entity.OrderExta;
import com.noah.lock.transaction.entity.OrderInfo;
import com.noah.lock.transaction.service.IOrderExtaService;
import com.noah.lock.transaction.service.IOrderInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;

@Service
public class OrderService {

    @Resource
    IOrderInfoService iOrderInfoService;

    @Resource
    IOrderExtaService iOrderExtaService;

    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public void doOrder() {

        String orderId = UUID.randomUUID().toString();

        try {
            //doOrderExtra(orderId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        OrderInfo order = new OrderInfo();

        order.setOrderId(orderId);
        order.setOrderName(orderId + System.currentTimeMillis());

        order.setDeleteMark(0);
        order.setGmtCreated(new Date());
        order.setGmtModified(new Date());

        iOrderInfoService.save(order);
    }

    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRES_NEW)
    public void doOrderExtra(String orderId) {

        OrderExta orderExta = new OrderExta();

        orderExta.setOrderId(orderId);
        orderExta.setOrderExtra("{}");
        orderExta.setDeleteMark(0);
        orderExta.setGmtCreated(new Date());
        orderExta.setGmtModified(new Date());


        iOrderExtaService.save(orderExta);

        int a = 1 / 0;

    }

}
