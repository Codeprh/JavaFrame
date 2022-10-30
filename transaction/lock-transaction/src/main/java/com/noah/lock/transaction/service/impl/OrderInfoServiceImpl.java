package com.noah.lock.transaction.service.impl;

import com.noah.lock.transaction.entity.OrderInfo;
import com.noah.lock.transaction.mapper.OrderInfoMapper;
import com.noah.lock.transaction.service.IOrderInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author noah
 * @since 2022-10-29
 */
@Service
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements IOrderInfoService {

}
