package com.noah.guava.Immutable;

import com.google.common.base.CharMatcher;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.primitives.Longs;

import java.util.ArrayList;

public class BiMapTest {


    public static void main1(String[] args) {
        ArrayList<String> list = new ArrayList<>();
        list.add("Apple");
        list.add("Banana");
        list.add("Cherry");

        System.out.println("原始列表：" + list);

        list.add(1, "Orange");

        System.out.println("插入后的列表：" + list);
    }

    public static void main(String[] args) {

        BiMap<String, Integer> userId = HashBiMap.create();
        userId.put("noah",18);
        userId.put("noah222",18);
        System.out.println(userId.inverse().get(18));

        String str="213212109fkhsadj";
        System.out.println(Longs.tryParse(CharMatcher.digit().retainFrom(str)));
    }
}
