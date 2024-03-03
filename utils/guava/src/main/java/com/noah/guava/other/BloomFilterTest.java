package com.noah.guava.other;

import cn.hutool.bloomfilter.BitSetBloomFilter;
import cn.hutool.bloomfilter.BloomFilterUtil;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.math.BigInteger;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.concurrent.TimeUnit;

public class BloomFilterTest {

    /**
     * 重排策略组缓存
     */
    private LoadingCache<Long, Object> rerankStrategyGrouCaches = CacheBuilder.newBuilder().expireAfterWrite(5, TimeUnit.MINUTES)
            .refreshAfterWrite(2, TimeUnit.MINUTES).build(new CacheLoader<Long, Object>() {
                @Override
                public Object load(Long groupId) throws Exception {
                    return null;
                }
            });


    public static void main(String[] args) {

        PriorityQueue<Integer> queue = new PriorityQueue<Integer>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2.compareTo(o1);
            }
        });

        queue.add(1);
        queue.add(3);
        queue.add(2);

        System.out.println(queue.remove());

    }

    public static void main1(String[] args) {
        //BloomFilter<Object> g = new BloomFilter<Object>();
        //BloomFilterStrategies g = new BloomFilterStrategies

        BitSetBloomFilter bitSet = BloomFilterUtil.createBitSet(1000, 10, 3);

        //BloomFilterUtil.createBitMap()
        bitSet.add("noah");

        BigInteger bigInteger = new BigInteger("2313213212321321");
        int i = bigInteger.hashCode();

        boolean contains = bitSet.contains("noah");
        System.out.println(contains);
        System.out.println(bitSet.contains("noah1"));
        System.out.println("hello world");
    }
}
