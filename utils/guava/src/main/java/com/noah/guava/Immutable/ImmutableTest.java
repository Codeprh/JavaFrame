package com.noah.guava.Immutable;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;

public class ImmutableTest {
    public static void main(String[] args) {

        ImmutableSet<String> colorNames = ImmutableSet.of(
                "red",
                "orange",
                "yellow",
                "green",
                "blue",
                "purple");
        colorNames.stream().forEach(s -> System.out.println(s));

        ImmutableList<String> immutableList = ImmutableList.copyOf(Lists.newArrayList("aa", "bb").subList(0, 1));
        immutableList.stream().forEach(s -> System.out.println(s));

    }

}
