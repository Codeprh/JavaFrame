package com.noah.guava.cacha;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

/**
 * 缓存穿透的解决方案
 */
@Slf4j
public class QueryServiceTest {

    @Test
    public void testQuery() {
        QueryService queryService = new QueryService();
        String name = "noah";
        log.info(queryService.query(name) + "");
        log.info(queryService.query(name) + "");
    }

    /**
     * 不同用户的并发查询
     */
    @Test
    @SneakyThrows
    public void testQueryThread() {

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        QueryService queryService = new QueryService();
        String name = "noah";

        IntStream.range(1, 400).forEach(i -> {
            executorService.execute(() -> {
                log.info("name:{},score:{}", name + i, queryService.queryWithCHM(name + i + "") + "");
                log.info("name:{},score:{}", name + i, queryService.queryWithCHM(name + i + "") + "");
                log.info("name:{},score:{}", name + i, queryService.queryWithCHM(name + i + "") + "");
            });
        });

        TimeUnit.SECONDS.sleep(5);
    }

    /**
     * 同一个用户的并发查询
     */
    @Test
    @SneakyThrows
    public void testSameQueryThread() {

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        QueryService queryService = new QueryService();
        String name = "noah";

        IntStream.range(1, 10).forEach(i -> {
            executorService.execute(() -> {
                log.info("name:{},score:{}", name, queryService.queryWithFCCHM(name + "") + "");
                log.info("name:{},score:{}", name, queryService.queryWithFCCHM(name + "") + "");
                log.info("name:{},score:{}", name, queryService.queryWithFCCHM(name + "") + "");
            });
        });

        TimeUnit.SECONDS.sleep(1);
        Set<Map.Entry<String, Future<Integer>>> entries = queryService.getFutureConcurrentHashMap().entrySet();
        for (Map.Entry<String, Future<Integer>> entry : entries) {
            entry.getValue().cancel(true);
        }

        TimeUnit.SECONDS.sleep(5);
    }


    @Test
    @SneakyThrows
    public void wrong_usage_whenCacheMiss_thenPut_thread() throws ExecutionException {
        LoadingCache<String, String> cache =
                CacheBuilder.newBuilder().build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String key) {
                        return fetchValueFromServer(key);
                    }
                });

        ExecutorService executorService = Executors.newFixedThreadPool(5);
        IntStream.range(1, 4).forEach(i -> {
            executorService.execute(() -> {
                log.info(cache.getUnchecked("noah"));
            });
        });

        TimeUnit.SECONDS.sleep(10);
    }


    @Test
    public void wrong_usage_whenCacheMiss_thenPut() throws ExecutionException {
        LoadingCache<String, String> cache =
                CacheBuilder.newBuilder().build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String key) {
                        return fetchValueFromServer(key);
                    }
                });

        String key = "kirito";
        String cacheValue = cache.get(key);

        //判断缓存不存在的时候，手动put方法
        //没有使用get，自动load的方法
        if ("".equals(cacheValue)) {
            cacheValue = fetchValueFromServer(key);
            cache.put(key, cacheValue);
        }
        cache.put(key, cacheValue);

        assertEquals(1, cache.size());
    }

    private String fetchValueFromServer(String key) {
        log.info("开始从db里面load数据，key:{}", key);
        return key.toUpperCase();
    }

}