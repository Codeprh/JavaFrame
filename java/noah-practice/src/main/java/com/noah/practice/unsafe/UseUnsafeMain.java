package com.noah.practice.unsafe;

import lombok.SneakyThrows;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class UseUnsafeMain {

    @SneakyThrows
    public static void main(String[] args) {
        // Unsafe 设置了构造方法私有，getUnsafe 获取实例方法包私有，在包外只能通过反射获取
        Field field = Unsafe.class.getDeclaredField("theUnsafe");
        field.setAccessible(true);
        Unsafe unsafe = (Unsafe) field.get(null);
        // Test 类是一个随手写的测试类，只有一个 String 类型的测试类
        Test test = new Test();
        test.ttt = "12345";
        unsafe.putLong(test, 12L, 2333L);
        System.out.println(test.value);

    }

    private static class Test {
        public String ttt;
        public String value;
    }
}
