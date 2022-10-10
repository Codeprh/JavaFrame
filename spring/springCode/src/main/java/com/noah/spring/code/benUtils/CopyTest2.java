package com.noah.spring.code.benUtils;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@ToString
@Data
public class CopyTest2 {
    public String outerName;
    public CopyTest2.InnerClass innerClass;
    public List<InnerClass> clazz;

    @ToString
    @Data
    public static class InnerClass {
        public String InnerName;
    }
}
