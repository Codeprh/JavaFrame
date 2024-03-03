package com.noah.practice.map;

public class HashCodeTest {

    public static void main(String[] args) {
        //a-x=1;b-y=31;
        //xy=ab
        String Aa = "Aa";
        String BB = "BB";
        System.out.println(Aa.hashCode());
        System.out.println(BB.hashCode());

        System.out.println((Aa + BB).hashCode());
        System.out.println((Aa + Aa).hashCode());
        System.out.println((BB + BB).hashCode());


    }
}
