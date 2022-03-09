package com.noah.lock.transaction.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.noah.lock.transaction.entity.Product;
import com.noah.lock.transaction.mapper.ProductMapper;
import com.noah.lock.transaction.service.IProductService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author noah
 * @since 2022-03-05
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {

    @Resource
    ProductMapper productMapper;

    @Override
    public Integer sellProduct(Long id) {
        return productMapper.sellProduct(id);
    }
}
