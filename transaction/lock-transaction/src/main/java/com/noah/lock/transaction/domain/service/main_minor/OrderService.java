package com.noah.lock.transaction.domain.service.main_minor;

import com.noah.lock.transaction.entity.OrderExta;
import com.noah.lock.transaction.entity.OrderInfo;
import com.noah.lock.transaction.service.IOrderExtaService;
import com.noah.lock.transaction.service.IOrderInfoService;
import org.springframework.aop.framework.AopContext;
import org.springframework.context.ApplicationContext;
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
    OrderExtraService orderExtraService;

    @Resource
    IOrderExtaService iOrderExtaService;

    @Resource
    OrderService orderService;

    @Resource
    ApplicationContext applicationContext;

    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public void doOrder() {

        String orderId = UUID.randomUUID().toString();

        //在这里对异常进行捕获，即使订单详情表抛出了异常，订单表的逻辑还是可以继续执行
        try {
            OrderService aopOrderService = (OrderService) AopContext.currentProxy();
            aopOrderService.doOrderExtra(orderId);
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

    //根据需求，两个事务应该相互独立的，所以订单详情表事务传播属性是：REQUIRES_NEW
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRES_NEW)
    public void doOrderExtra(String orderId) {

        OrderExta orderExta = new OrderExta();

        orderExta.setOrderId(orderId);
        orderExta.setOrderExtra("{}");
        orderExta.setDeleteMark(0);
        orderExta.setGmtCreated(new Date());
        orderExta.setGmtModified(new Date());

        iOrderExtaService.save(orderExta);

        //模拟业务抛出异常
        int a = 1 / 0;

    }

}
