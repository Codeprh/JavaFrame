package com.noah.spring.transaction.service;

import com.noah.spring.transaction.service.impl.LettuceConfigServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * 描述:
 * aop测试业务
 *
 * @author Noah
 * @create 2021-11-23 5:19 下午
 */
@Service
@Slf4j
public class AopService {

    @Autowired
    ApplicationContext applicationContext;

    /**
     * 发射调用bean的方法，然后aop是否生效
     *
     * @param type
     */
    public void testAop(Integer type) {

        try {
            Map<String, LettuceConfigServiceImpl> beans = applicationContext.getBeansOfType(LettuceConfigServiceImpl.class);

            LettuceConfigServiceImpl serviceImpl = beans.get("lettuceConfigServiceImpl");
            Class<? extends LettuceConfigServiceImpl> implClass = serviceImpl.getClass();

            Method[] methods = implClass.getDeclaredMethods();
            for (Method method : methods) {
                //log.info(method.getName());
            }
            Method right = implClass.getDeclaredMethod("right", Integer.class);
            right.invoke(serviceImpl, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
