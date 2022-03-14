package com.noah.practice.java8;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiConsumer;
import java.util.stream.IntStream;

@Slf4j
public class BiConsumerUse {

    public static void main(String[] args) {

        //这里写了很多逻辑。xxx
        String bName = "noahC";
        AtomicLong atomicLong = new AtomicLong();

        IntStream.rangeClosed(1, 3).forEach(i -> {

            BData bData = buildData((isFirst, bdata) -> {

                bdata.setId(atomicLong.addAndGet(1));
                bdata.setBName(bName);
                bdata.setIsFirst(i % 2 == 0);

            });

            System.out.println(bData);

        });

    }

    public static BData buildData(BiConsumer<Boolean, BData> detailConsumer) {

        //这里写了很多逻辑。xxx
        boolean a = true;

        BData bData = new BData();
        bData.setName("cute");

        detailConsumer.accept(a, bData);

        return bData;
    }

    public static <T> void setAwardsIndex(List<T> list, BiConsumer<T, Integer> indexSetter) {
        for (int i = 0; i < list.size(); i++) {
            indexSetter.accept(list.get(i), i);
        }
    }

    @Data
    static class BData {
        private Long id;
        private String name;
        private String bName;
        private Boolean isFirst;
    }
}
