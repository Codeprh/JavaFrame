package com.noah.guava.Immutable;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import com.google.common.collect.Multimaps;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MultimapTest {

    public static void main(String[] args) {

        Multimap<String, Integer> result = HashMultimap.create();
        result.putAll("1", Lists.newArrayList(1, 2));
        result.putAll("1", Lists.newArrayList(1, 2, 3));
        result.putAll("1", null);
        System.out.println(result);
    }
    public static void main2(String[] args) {

        ListMultimap<Long, Long> result = ArrayListMultimap.create();

        for (int i = 0; i < 10; i++) {
            result.put(1L, (long) i);
            result.put(2L, (long) i);
        }

        Map<Long, List<Long>> map = Multimaps.asMap(result)
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> Lists.newArrayList(entry.getValue()),
                        (list1, list2) -> {
                            list1.addAll(list2);
                            return list1;
                        }
                ));
        System.out.println(map);
    }

    public static void main1(String[] args) {
        ListMultimap<String, Integer> treeListMultimap =
                MultimapBuilder.treeKeys().arrayListValues().build();

        ListMultimap<Long, List<Long>> build = MultimapBuilder.hashKeys().arrayListValues().build();

    }
}
