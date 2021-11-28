package com.noah.spring.transaction.mapper;


import com.noah.spring.transaction.entity.LettuceConfig;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 描述:
 * jpa实现数据库持久层
 *
 * @author Noah
 * @create 2021-11-16 4:52 下午
 */
public interface JPALettuceConfigRepository extends JpaRepository<LettuceConfig, Long> {

}
