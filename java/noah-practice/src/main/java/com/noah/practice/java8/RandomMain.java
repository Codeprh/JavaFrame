package com.noah.practice.java8;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RandomMain {

    public static void main(String[] args) {

        int hashCode = "4662abca-3f4f-4814-abf6-15adc9736d82".hashCode();
        Random random = new Random(hashCode);

        List<Integer> collect = IntStream.rangeClosed(0, 10).boxed().collect(Collectors.toList());

        long l = System.currentTimeMillis();

        Collections.shuffle(collect, random);

        System.out.println(System.currentTimeMillis() - l);

        System.out.println(collect);
        System.out.println("[2, 8, 6, 1, 7, 4, 3, 0, 10, 5, 9]");

    }
}
