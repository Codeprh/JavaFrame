package com.noah.lock.transaction.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.noah.lock.transaction.entity.Product;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author noah
 * @since 2022-03-05
 */
public interface IProductService extends IService<Product> {

    public Integer sellProduct(Long id);

    List<Product> query00(Integer stock);
}
