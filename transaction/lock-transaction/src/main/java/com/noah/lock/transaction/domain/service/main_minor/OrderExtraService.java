package com.noah.lock.transaction.domain.service.main_minor;

import com.noah.lock.transaction.entity.OrderExta;
import com.noah.lock.transaction.service.IOrderExtaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class OrderExtraService {

    @Resource
    IOrderExtaService iOrderExtaService;

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
