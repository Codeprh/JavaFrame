package com.noah.practice.guc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * ArrayList的使用
 */
public class NoahUseList {

    public static void main(String[] args) {
        listAdd();
    }

    /**
     * 1、ArrayList的add扩容次数
     * 1-1、每次扩容是，当前容量的1.5倍（oldCapacity+(oldCapacity>>1)）
     * 2、各种arrayList的区别
     */
    public static void listAdd() {

        //add 10次，扩容了7次
        //List<String> arrayList = new ArrayList<>(0);

        //add 10次，初始化的时候扩容1次
        List<String> arrayList = new ArrayList<>();

        arrayList.add("a");
        arrayList.add("b");
        arrayList.add("c");
        arrayList.add("d");
        arrayList.add("e");

        arrayList.add("f");
        arrayList.add("j");
        arrayList.add("h");
        arrayList.add("i");
        arrayList.add("j");

        //java.util.Collections.EmptyList是一个内部类，没有实现父类的add()方法，直接抛出UnsupportedOperationException
        //实际的用途：在需要性能优化的时候，直接返回一个空的List，因为它是一个倍final修饰的常量。无需new的操作
        List<String> emptyList = Collections.EMPTY_LIST;
        emptyList.add("aaa");

        //java.util.Arrays.ArrayList是一个内部类，跟上面一样。无法直接add()操作
        //实际的用途：返回一个不可以修改长度的list，可以对数组里面的元素进行修改操作
        List<String> asList = Arrays.asList();
        asList.add("bbb");
    }
}
