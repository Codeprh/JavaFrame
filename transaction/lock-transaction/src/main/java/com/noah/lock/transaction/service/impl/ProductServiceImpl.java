package com.noah.lock.transaction.service.impl;

import com.noah.lock.transaction.entity.Product;
import com.noah.lock.transaction.mapper.ProductMapper;
import com.noah.lock.transaction.service.IProductService;
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
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {

}
