package com.noah.guava.Immutable;

import com.google.common.base.CharMatcher;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Lists;

public class MultisetTest {

    public static void main(String[] args) {

        HashMultiset<String> stringHashMultiset = HashMultiset.create();
        stringHashMultiset.addAll(Lists.newArrayList("aaa", "bb","aaa"));

        int aaa = stringHashMultiset.count("aaa");
        stringHashMultiset.add("aaa");
        int aaa1 = stringHashMultiset.count("aaa");
        System.out.println(aaa);
        System.out.println(aaa1);
        stringHashMultiset.add(null);
        int aaa2 = stringHashMultiset.count(null);
        System.out.println(aaa2);

    }
    public static void main1(String[] args) {

        String allX = "321ab--- " +
                "";

        System.out.println(CharMatcher.digit().retainFrom(allX));

        HashMultiset<String> stringHashMultiset = HashMultiset.create();
        stringHashMultiset.addAll(Lists.newArrayList("aaa", "bb"));

        System.out.println(stringHashMultiset.size());

        stringHashMultiset.elementSet().stream().forEach(ss -> System.out.println(ss));
        System.out.println(stringHashMultiset.elementSet().size());

        stringHashMultiset.count("aaa");
    }
}
