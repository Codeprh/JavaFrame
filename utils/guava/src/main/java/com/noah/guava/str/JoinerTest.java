package com.noah.guava.str;

import com.google.common.base.Joiner;

public class JoinerTest {

    public static void main(String[] args) {
        Joiner joiner = Joiner.on(";").skipNulls();
        System.out.println(joiner.join("Harry", null, "Ron", "Hermione"));
    }
}
