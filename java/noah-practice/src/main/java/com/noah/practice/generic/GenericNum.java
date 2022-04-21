package com.noah.practice.generic;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * 泛型方法
 * <p>
 * 逆变(? super)：放宽的对父类型的约束，使得父类型的泛型对象可以进行赋值，失去了获取该泛型的能力
 * 说法1：1、不能调用get方法，2、只作用的消费端
 * 说法2：1、取元素只能放到Object，2、能存入元素
 * <p>
 * 协变（? extend）：是放宽对子类型的约束，使得子类型的泛型对象可以进行赋值，失去了传递该泛型对象的能力
 * 说法1：1、只作用生产者，2、不能调用add()
 * 说法2：1、能够取出来，2、不能存入元素
 *
 * 总结extend读，super写
 */
@Slf4j
public class GenericNum {

    public static void main(String[] args) {
        genericExtend();
    }

    //--------------------------
    //逆变演示
    //--------------------------

    public static void genericSuper() {

        NoahFilter<Number> filter = new NoahFilter<Number>() {
            @Override
            public boolean test(Number elememt) {
                return elememt.intValue() > 100;
            }
        };

        List<Integer> integerList = new ArrayList<>();

        integerList.add(60);
        integerList.add(101);

        removeIf(integerList, filter);
    }

    /**
     * 判断过滤接口
     *
     * @param <E>
     */
    interface NoahFilter<E> {
        boolean test(E elememt);
    }

    /**
     * 根据fiter，过滤list元素，返回过滤的元素
     *
     * @param list
     * @param filter
     * @param <E>
     * @return
     */
    public static <E> List<E> removeIf(List<E> list, NoahFilter<? super E> filter) {

        List<E> removeList = new ArrayList<>();
        for (E e : list) {

            if (filter.test(e)) {
                removeList.add(e);
            }
        }
        list.removeAll(removeList);
        return removeList;
    }

    //--------------------------
    //逆变演示end
    //--------------------------

    /**
     * 协变
     */
    public static void genericExtend() {

        List<Double> list = new ArrayList<>();

        list.add(1d);
        list.add(2d);
        list.add(3d);

        //error:泛型不可变
        //sum(list);

        double sumExtend = sumExtend(list);
        log.info("sumExtend=" + sumExtend);
    }

    public static double sumExtend(List<? extends Number> list) {

        //error:协变之后不能调用：协变后对象的含有泛型参数的方法了。比如：add()方法，但是get()可行
        //list.add(1);
        list.get(0);
        double r = 0;

        for (Number number : list) {
            r += number.doubleValue();
        }

        return r;
    }

    public static double sum(List<Number> list) {

        double r = 0;

        for (Number number : list) {
            r += number.doubleValue();
        }

        return r;
    }

    /**
     * 泛型不可变
     */
    public void genericImmutable() {

        ArrayList<Object> objectList = new ArrayList<>();
        ArrayList<String> stringList = new ArrayList<>();

        //error：泛型不可变
        //objectList = stringList;
        objectList.add(new Bdata());
        stringList.get(0);

    }

    static class Bdata {
        private String name;
    }
}
