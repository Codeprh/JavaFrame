package com.noah.spring.transaction.aspect.advisor;

import org.springframework.aop.support.StaticMethodMatcherPointcut;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * 描述:
 * todo：1、加上注解，就可以aop子类的父类方法
 * todo：2、注解的子方法无效
 * <p>
 * 切面判断：aop拦截，子类的父类的父类，所有方法。以AopEntity为栗子
 *
 * @author Noah
 * @create 2021-11-25 6:03 下午
 */
public class EntityPointcut extends StaticMethodMatcherPointcut implements Serializable {

    @Override
    public boolean matches(Method method, Class<?> targetClass) {

        if (targetClass == null) {
            return false;
        }

        Class<?> declaringClass = method.getDeclaringClass();
        String superClassName = declaringClass.getName();

        if (!targetClass.getName().equals("com.noah.spring.transaction.entity.AopEntity")) {
            // 不是S类的方法不进切面
            return false;
        }

        return superClassName.equals("com.noah.spring.transaction.entity.AopEntity") || superClassName.equals("com.noah.spring.transaction.service.AopEntitySubService");
    }

}
