package com.noah.guava.str;

import com.google.common.base.CaseFormat;
import com.google.common.base.CharMatcher;

public class CharMatcherTest {

    public static void main(String[] args) {
        String string="abc---1---23";
        String theDigits = CharMatcher.digit().retainFrom(string); // only the digits

        String noControl = CharMatcher.javaIsoControl().removeFrom(string); // remove control characters

        String abc = CharMatcher.any().removeFrom("abc");

        String removeFrom = CharMatcher.is('a').removeFrom("bazaar");
        System.out.println(removeFrom);

        System.out.println(theDigits);

        String constantName = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, "CONSTANT_NAME");// returns "constantName"


    }
}
