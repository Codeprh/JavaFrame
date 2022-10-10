package com.noah.practice.list;

import lombok.SneakyThrows;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class UnSafeArrayList {

    @SneakyThrows
    public static void main(String[] args) {

        List<Integer> arrayList = new NoahArrayList<>();

        for (int i = 0; i < 9; i++) {
            arrayList.add(i);
        }

        new Thread(() -> arrayList.add(9), "noah1").start();
        new Thread(() -> arrayList.add(10), "noah2").start();

        TimeUnit.SECONDS.sleep(1);
        System.out.println("arrayList=" + arrayList + ",size=" + arrayList.size());
    }
}
