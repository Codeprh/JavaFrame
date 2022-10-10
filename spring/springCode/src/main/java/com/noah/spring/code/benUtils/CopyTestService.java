package com.noah.spring.code.benUtils;

import org.springframework.beans.BeanUtils;

public class CopyTestService {

    public static void main(String[] args) {

        fixInnerClassCopy();

        //innerClassCopy();
    }

    private static void fixInnerClassCopy() {

        CopyTest1 test1 = new CopyTest1();
        test1.outerName = "hahaha";

        CopyTest1.InnerClass innerClass = new CopyTest1.InnerClass();
        innerClass.InnerName = "hohoho";
        test1.innerClass = innerClass;

        System.out.println(test1.toString());

        CopyTest2 test2 = new CopyTest2();

        test2.innerClass = new CopyTest2.InnerClass();

        BeanUtils.copyProperties(test1, test2);
        BeanUtils.copyProperties(test1.innerClass, test2.innerClass);

        System.out.println(test2.toString());
    }

    private static void innerClassCopy() {
        CopyTest1 test1 = new CopyTest1();
        test1.outerName = "hahaha";

        CopyTest1.InnerClass innerClass = new CopyTest1.InnerClass();
        innerClass.InnerName = "hohoho";
        test1.innerClass = innerClass;

        System.out.println(test1.toString());

        CopyTest2 test2 = new CopyTest2();
        BeanUtils.copyProperties(test1, test2);

        System.out.println(test2.toString());
    }

}
