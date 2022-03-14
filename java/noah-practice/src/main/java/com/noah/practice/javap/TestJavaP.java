package com.noah.practice.javap;

public class TestJavaP {

    public static void main(String[] args) {
        one();
    }

    public static void one() {
        int a = 100;
        callMethod(a);
    }

    public static void two() {

        Integer a = new Integer(100);
        callMethod(a);
    }

    public static int callMethod(Object a) {
        int ai = (int) a;
        return ai + ai;
    }
}
