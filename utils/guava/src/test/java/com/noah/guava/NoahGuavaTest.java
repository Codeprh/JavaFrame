package com.noah.guava;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.CacheStats;
import com.google.common.cache.LoadingCache;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.LongStream;

import static org.junit.Assert.*;

@Slf4j
public class NoahGuavaTest {

    private String fetchValueFromServer(String key) {
        return key.toUpperCase();
    }

    @Test
    public void whenCacheMiss_thenFetchValueFromServer() throws ExecutionException {
        LoadingCache<String, String> cache =
                CacheBuilder.newBuilder().build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String key) {
                        return fetchValueFromServer(key);
                    }
                });

        assertEquals(0, cache.size());
        assertEquals("HELLO", cache.getUnchecked("hello"));
        assertEquals("HELLO", cache.get("hello"));
        assertEquals(1, cache.size());
    }

    @Test
    public void whenPreloadCache_thenPut() {
        LoadingCache<String, String> cache =
                CacheBuilder.newBuilder().build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String key) {
                        return fetchValueFromServer(key);
                    }
                });

        String key = "noah";
        cache.put(key, fetchValueFromServer(key));

        assertEquals(1, cache.size());
    }

    // 注意这是一个反面示例
    @Test
    public void wrong_usage_whenCacheMiss_thenPut() throws ExecutionException {
        LoadingCache<String, String> cache =
                CacheBuilder.newBuilder().build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String key) {
                        return "";
                    }
                });

        String key = "noah";
        String cacheValue = cache.get(key);
        if ("".equals(cacheValue)) {
            cacheValue = fetchValueFromServer(key);
            cache.put(key, cacheValue);
        }
        cache.put(key, cacheValue);

        assertEquals(1, cache.size());
    }

    @Test
    public void whenReachMaxSize_thenEviction() throws ExecutionException {
        LoadingCache<String, String> cache =
                CacheBuilder.newBuilder().maximumSize(3).build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String key) {
                        return fetchValueFromServer(key);
                    }
                });

        cache.get("one");
        cache.get("two");
        cache.get("three");
        cache.get("four");
        assertEquals(3, cache.size());
        assertNull(cache.getIfPresent("one"));
        assertEquals("FOUR", cache.getIfPresent("four"));
    }

    @Test
    public void whenReachMaxSize_thenEviction_v2() throws ExecutionException {
        LoadingCache<String, String> cache =
                CacheBuilder.newBuilder().maximumSize(3).build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String key) {
                        return fetchValueFromServer(key);
                    }
                });

        cache.get("one");
        cache.get("two");
        cache.get("three");
        // access one
        cache.get("one");
        cache.get("four");
        assertEquals(3, cache.size());
        assertNull(cache.getIfPresent("two"));
        assertEquals("ONE", cache.getIfPresent("one"));
    }

    @Test
    public void whenEntryIdle_thenEviction()
            throws InterruptedException, ExecutionException {

        LoadingCache<String, String> cache =
                CacheBuilder.newBuilder().expireAfterAccess(1, TimeUnit.SECONDS).build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String key) {
                        return fetchValueFromServer(key);
                    }
                });

        cache.get("noah");
        assertEquals(1, cache.size());

        cache.get("noah");
        Thread.sleep(2000);

        assertNull(cache.getIfPresent("noah"));
    }

    @Test
    public void whenInvalidate_thenGetNull() throws ExecutionException {
        LoadingCache<String, String> cache =
                CacheBuilder.newBuilder()
                        .build(new CacheLoader<String, String>() {
                            @Override
                            public String load(String key) {
                                return fetchValueFromServer(key);
                            }
                        });

        String name = cache.get("noah");
        assertEquals("KIRITO", name);

        cache.invalidate("noah");
        assertNull(cache.getIfPresent("noah"));
    }

    @Test
    public void whenCacheRefresh_thenLoad()
            throws InterruptedException, ExecutionException {

        LoadingCache<String, String> cache =
                CacheBuilder.newBuilder().expireAfterWrite(1, TimeUnit.SECONDS).build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String key) throws InterruptedException {
                        Thread.sleep(2000);
                        return key + ThreadLocalRandom.current().nextInt(100);
                    }
                });

        String oldValue = cache.get("noah");

        new Thread(() -> {
            cache.refresh("noah");
        }).start();

        // make sure another refresh thread is scheduling
        Thread.sleep(500);

        String val1 = cache.get("noah");

        //刷新的时候，返回旧值
        assertEquals(oldValue, val1);

        // make sure refresh cache
        Thread.sleep(2000);

        String val2 = cache.get("noah");
        assertNotEquals(oldValue, val2);

    }

    @Test
    public void whenTTL_thenRefresh() throws ExecutionException, InterruptedException {
        LoadingCache<String, String> cache =
                CacheBuilder.newBuilder().refreshAfterWrite(1, TimeUnit.SECONDS).build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String key) {
                        return key + ThreadLocalRandom.current().nextInt(100);
                    }
                });

        String first = cache.get("noah");
        Thread.sleep(1000);
        String second = cache.get("noah");

        assertNotEquals(first, second);
    }

    @Test
    public void whenRecordStats_thenPrint() throws ExecutionException {
        LoadingCache<String, String> cache =
                CacheBuilder.newBuilder().maximumSize(100).recordStats().build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String key) {
                        return fetchValueFromServer(key);
                    }
                });

        cache.get("one");
        cache.get("two");
        cache.get("three");
        cache.get("four");

        cache.get("one");
        cache.get("four");

        CacheStats stats = cache.stats();
        System.out.println(stats);
    }

    @Test
    public void whenRemoval_thenNotify() throws ExecutionException {
        LoadingCache<String, String> cache =
                CacheBuilder.newBuilder().maximumSize(3)
                        .removalListener(
                                cacheItem -> System.out.println(cacheItem + " is removed, cause by " + cacheItem.getCause()))
                        .build(new CacheLoader<String, String>() {
                            @Override
                            public String load(String key) {
                                return fetchValueFromServer(key);
                            }
                        });

        cache.get("one");
        cache.get("two");
        cache.get("three");
        cache.get("four");
    }

    @SneakyThrows
    @Test
    public void guava_test_time() {

        LoadingCache<Long, String> activityCache = CacheBuilder.newBuilder().expireAfterWrite(10, TimeUnit.SECONDS)
                .refreshAfterWrite(6, TimeUnit.SECONDS).build(new CacheLoader<Long, String>() {
                    @Override
                    public String load(Long id) throws Exception {
                        log.info("触发了db查询，id:{}", id);
                        id++;
                        return id.toString();
                    }
                });


        LongStream.range(1, 3).forEach(i -> {
            try {
                log.info("r=" + activityCache.get(i));
                //TimeUnit.SECONDS.sleep(12);
            } catch (Exception e) {
                e.printStackTrace();
            }

        });

        while (true) {
            TimeUnit.SECONDS.sleep(1);
            log.info("r=" + activityCache.get(1L));
        }
    }

}