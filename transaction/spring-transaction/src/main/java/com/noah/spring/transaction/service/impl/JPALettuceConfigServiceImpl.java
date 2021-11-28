package com.noah.spring.transaction.service.impl;

import com.noah.spring.transaction.mapper.JPALettuceConfigRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 描述:
 * JPA机制实现
 *
 * @author Noah
 * @create 2021-11-16 4:43 下午
 */
@Service("jpaLettuceConfigServiceImpl")
@Slf4j
public class JPALettuceConfigServiceImpl extends LettuceConfigServiceImpl {

    @Resource
    JPALettuceConfigRepository jpaLettuceConfigRepository;

    @Override
    public void saveWrong(Integer type) {

        try {

            this.thisSave(type);

        } catch (Exception e) {
            log.error("error");
        }
    }

    @Override
    public void right(Integer type) {
        jpaLettuceConfigRepository.save(generatorConfig(type));
        if (type == 100) {
            throw new RuntimeException("系统异常");
        }
    }

    @Override
    public void thisSave(Integer type) {

        jpaLettuceConfigRepository.save(generatorConfig(type));
        if (type == 100) {
            throw new RuntimeException("系统异常");
        }
    }
}
