package com.noah.practice.guc;

import com.google.common.collect.Lists;

import java.util.*;

/**
 * ArrayList的使用
 * <p>
 * 坑点：
 * 坑1：elementData = EMPTY_ELEMENTDATA;会导致每次add元素都会触发扩容操作
 * 坑2：复用Object[]的空间，对应优1
 * 坑3：addAll()空集合的list，返回的是false值，如果使用了返回值，然后抛异常，这就是坑了
 * 坑4：removeAll()，仅仅只是对元素进行了null判断，没有判空
 * 坑5：只有try-finally，没有catch都是流氓：java.util.ArrayList#batchRemove()
 * <p>
 * 优点：
 * 优1：arrayList.clear();复用List内部的Object[]数组
 * 优2：elementData[--size] = null; // clear to let GC do its work，移除的数组index位置设置为null
 * 优3：transient，表示不需要序列化该属性，Object[] elementData，我们存放数据的数组，避免序列化elementData.length>size额外的空间序列化
 * 优4：writeObject，表示list的态度，元素的任何修改都比序列化的优先级高
 * 优5：clone()方法，是根据size来克隆，等价优点3
 * 优6：同优点4，迭代器在迭代的时候，发生元素修改modCount更改，直接fail-fast：ConcurrentModificationException
 * 优7：arrayList.stream.forEeach()本质调用的是ArrayList的内部类：Spliterator
 */
public class NoahUseList {

    public static void main(String[] args) {
        //listAdd();
        //listInit();
        //useArrayCopy();
        //doIndex();
        //addAll();
        useStreamList();
    }

    public static void useStreamList() {
        List<String> arrayList = new ArrayList<>();
        arrayList.add("a");
        arrayList.add("b");
        arrayList.add("c");
        arrayList.add("d");

        //优点7：
        //arrayList.stream().forEach(s -> System.out.println(s));

        Spliterator<String> spliterator = arrayList.spliterator();

        Spliterator<String> stringSpliterator = spliterator.trySplit();

        boolean tryAdvance = stringSpliterator.tryAdvance(s -> System.out.println(s + ",2tryAdvance"));
        if (tryAdvance) {
            stringSpliterator.forEachRemaining(s -> System.out.println(s + ",2forEachRemaining"));
        }

        spliterator.forEachRemaining(s -> System.out.println(s+"1forEachRemaining"));

    }

    public static void addAll() {

        List<String> arrayList = new ArrayList<>();

        //坑3：
        //boolean b = arrayList.addAll(new ArrayList<>());
        arrayList.add("a");
        arrayList.add("b");
        arrayList.add("c");
        arrayList.add("d");

        List<String> removeList = new ArrayList<>();
        removeList.add("b");
        removeList.add("d");

        arrayList.removeAll(removeList);
        System.out.println(arrayList);

        return;
    }

    public static void doIndex() {

        List<String> arrayList = new ArrayList<>(100);
        arrayList.add("list");

        arrayList.set(0, "a0");
        arrayList.set(1, "a1");
        arrayList.set(50, "a50");
        System.out.println(arrayList);

    }

    public static void forList() {

        List<String> arrayList = new ArrayList<>();

        List<List<String>> doFor = Lists.newArrayList();
        for (List<String> df : doFor) {
            arrayList = df;
            //arrayList执行xx业务之后

            //优1：复用List内部的Object[]数组
            arrayList.clear();
            //坑2：new Object[]数组扩容的业务
            //arrayList = new ArrayList<>();
        }

    }

    public static void useArrayCopy() {

        Integer[] elementData = new Integer[]{0, 1, 2, 3};
        Integer[] copyOf = Arrays.copyOf(elementData, 3 + 3);

        System.out.println(elementData);
        System.out.println(copyOf);

        Integer[] newCopyOf = new Integer[3];

        System.arraycopy(copyOf, 1, newCopyOf, 0, 2);
        System.out.println(newCopyOf);
    }

    public static void listInit() {

        //坑1：
        List<String> arrayList = new ArrayList<>(new ArrayList<>());

    }

    /**
     * 1、ArrayList的add扩容次数
     * 1-1、每次扩容是，当前容量的1.5倍（oldCapacity+(oldCapacity>>1)）
     * 2、各种arrayList的区别
     */
    public static void listAdd() {

        //坑1：add 10次，扩容了7次
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
