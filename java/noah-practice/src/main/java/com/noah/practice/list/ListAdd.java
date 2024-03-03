package com.noah.practice.list;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

public class ListAdd {
    public static void main(String[] args) {
        List<PP> aa = Lists.newArrayList();
        PP p = new PP();
        p.setName("pp");
        aa.add(p);

        add1(aa);
        System.out.println(aa);
    }

    public static void add1(List<PP> aa) {
        add2(aa);
    }

    public static void add2(List<PP> aa) {

        System.out.println(aa.hashCode());
        for (PP p2:aa){
            p2.setName("zz");
        }
        System.out.println(aa.hashCode());
        PP p1 = new PP();
        p1.setName("bb");

        aa.addAll(Lists.newArrayList(p1));
        //System.out.println("1");
    }

    @Data
    static class PP {
        String name;
    }
}
