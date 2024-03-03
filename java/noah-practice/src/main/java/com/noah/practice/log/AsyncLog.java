package com.noah.practice.log;

import cn.hutool.core.text.StrFormatter;
import cn.hutool.json.JSONUtil;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
public class AsyncLog {
    static ExecutorService executorService = Executors.newFixedThreadPool(11);

    @SneakyThrows
    public static void main(String[] args) {


        for (int i = 0; i < 100; i++) {

            List<String> list = IntStream.rangeClosed(1,100000).boxed().map(String::valueOf).collect(Collectors.toList());
            List<String> list2 = IntStream.rangeClosed(1,100000).boxed().map(String::valueOf).collect(Collectors.toList());

            asyncLog(() -> StrFormatter.format("i am :{}", JSONUtil.toJsonStr(list)));
            TimeUnit.MILLISECONDS.sleep(10);
            list.addAll(list2);
            System.out.println(i);
        }
    }

    public static void asyncLog(Supplier<String> supplier) {

        executorService.execute(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(10);
                String logStr = supplier.get();
                //log.info("asyncLog:{},traceId:{}", logStr);
            } catch (Exception e) {
                log.error("RecommendAsync.asyncLog", e);
            }
        });

    }
}
