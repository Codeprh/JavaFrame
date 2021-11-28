package com.noah.practice.reflect;

import java.lang.reflect.Method;

/**
 * 描述:
 * 测试主类：反射调用方法，不是根据传参值决定的，而是根据参数类型决定的
 *
 * @author Noah
 * @create 2021-11-24 8:00 下午
 */
public class MainTest {

    public static void main(String[] args) {
        MainTest test = new MainTest();
        test.right();
    }

    public void right() {
        try {

            //走Integer
            Method age = getClass().getDeclaredMethod("age", Integer.class);
            age.invoke(this, 30);
            age.invoke(this, Integer.valueOf("31"));

            //走int
            Method ageMethod = getClass().getDeclaredMethod("age", Integer.TYPE);
            ageMethod.invoke(this, 30);
            ageMethod.invoke(this, Integer.valueOf("31"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void age(int age) {
        System.out.println("int,age=" + age);
    }

    private void age(Integer age) {
        System.out.println("Integer,age=" + age);
    }
}
