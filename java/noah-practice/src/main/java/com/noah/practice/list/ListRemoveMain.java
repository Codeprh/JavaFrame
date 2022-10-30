package com.noah.practice.list;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ListRemoveMain {

    public static void main(String[] args) {

        safeRemove();
    }

    private static void safeRemove() {
        List<String> list = new ArrayList<>();

        list.add("noah");
        list.add("java");
        //list.add("nodeJs");

        Iterator<String> iterator = list.iterator();

        while (iterator.hasNext()) {

            System.out.println("123");
            String next = iterator.next();
            list.remove(next);
            if (next.equalsIgnoreCase("noah")) {
            }
        }
        System.out.println(list.toString());
    }

    private static void jdkRemove() {

        List<String> list = new ArrayList<>();

        list.add("noah");
        list.add("java");
        list.add("nodeJs");

        list.removeIf(next -> next.equalsIgnoreCase("nodeJs"));
        System.out.println(list.toString());

    }
}
