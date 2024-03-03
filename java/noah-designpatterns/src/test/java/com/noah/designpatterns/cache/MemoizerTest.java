//package com.noah.designpatterns.cache;
//
//import com.noah.designpatterns.cache.app.ScoreQuery;
//import lombok.SneakyThrows;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.Test;
//
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.TimeUnit;
//import java.util.stream.IntStream;
//
//@Slf4j
//public class MemoizerTest {
//
//    /**
//     * 同一个用户的并发查询
//     */
//    @Test
//    @SneakyThrows
//    public void testSameQueryThread() {
//
//        ExecutorService executorService = Executors.newFixedThreadPool(10);
//        Memoizer memoizer = new Memoizer(new ScoreQuery());
//        String name = "noah";
//
//        IntStream.range(1, 10).forEach(i -> {
//            executorService.execute(() -> {
//                try {
//                    log.info("name:{},score:{}", name, memoizer.compute(name + "") + "");
//                    log.info("name:{},score:{}", name, memoizer.compute(name + "") + "");
//                    log.info("name:{},score:{}", name, memoizer.compute(name + "") + "");
//                } catch (Exception e) {
//                    log.error("error", e);
//                }
//            });
//        });
//
//        TimeUnit.SECONDS.sleep(10);
//    }
//
//
//}