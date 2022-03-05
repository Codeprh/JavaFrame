package com.noah.lock.transaction.service.impl;

import com.noah.lock.transaction.entity.Orders;
import com.noah.lock.transaction.mapper.OrdersMapper;
import com.noah.lock.transaction.service.IOrdersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author noah
 * @since 2022-03-05
 */
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements IOrdersService {

}
