package com.noah.lock.transaction.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.noah.lock.transaction.entity.Product;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author noah
 * @since 2022-03-05
 */
public interface ProductMapper extends BaseMapper<Product> {

    @Update("update product set stock = stock - 1 where stock > 0 and id = #{id}")
    public Integer sellProduct(@Param("id") Long id);

}
