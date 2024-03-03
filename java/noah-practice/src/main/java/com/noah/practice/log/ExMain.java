package com.noah.practice.log;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Slf4j
public class ExMain {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        Callable<String> call = () -> {
            return methodError();
        };

        Future<String> submit = executorService.submit(call);
        try {
            String s = submit.get();
        } catch (Exception e) {
            log.info("i am try-catch info:{}", JSONUtil.toJsonStr(e),e);
            log.warn("i am try-catch error:{}", JSONUtil.toJsonStr(e), e);
        }
    }

    public static String methodError() {
        if (1 == 1) {
            log.info("hello noah");
            throw new RuntimeException("i am noah error");
        }
        return null;
    }
}
