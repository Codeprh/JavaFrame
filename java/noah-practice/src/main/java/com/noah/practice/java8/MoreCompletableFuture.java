package com.noah.practice.java8;

import com.google.common.collect.Lists;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class MoreCompletableFuture {

    static ExecutorService executor = Executors.newFixedThreadPool(10);

    @SneakyThrows
    public static void main(String[] args) {

        final AtomicInteger count = new AtomicInteger();
        final BlockingQueue<Object> ref = new LinkedBlockingQueue<>(1);

        final int timeout = 5;
        List<String> selected = Lists.newArrayList("noah", "cute", "water");
        selected.forEach(se -> {

            CompletableFuture.supplyAsync(() -> {

                if (ref.size() > 0) {
                    return null;
                }

                doReq(se);

                return "success";
            }, executor).whenComplete((v, t) -> {

                if (t == null) {
                    ref.offer(v);
                } else {
                    int incrementAndGet = count.incrementAndGet();
                    if (incrementAndGet >= selected.size()) {
                        ref.offer(t);
                    }
                }

            });
        });

        Object ret = ref.poll(timeout, TimeUnit.SECONDS);
        if (ret instanceof Throwable) {
            throw new RuntimeException("i am finish error,timeout || all error");
        }

        log.info("ret:{}", ret);

    }

    @SneakyThrows
    public static void doReq(String name) {

        Random random = new Random();

        int nextInt = random.nextInt(5);
        if (nextInt % 2 == 0) {
            throw new RuntimeException("i am:" + name + ",error");
        }
        TimeUnit.SECONDS.sleep(nextInt);

        log.info("i am:{},sleep:{}", name, nextInt);

    }
}
