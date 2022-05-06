package com.noah.dubbo.loadbalance.algorithm;


import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.apache.dubbo.common.utils.AtomicPositiveInteger;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ThreadLocalRandom;

public class RoundRobinLoadBalanceTest {

    static class TestRun {
        public static void main(String[] args) {
            int[] arr = new int[]{33400, 33400, 66700, 66700, 100000, 100000, 133300, 133300, 166600, 166600};
            int r=0;
            for (int i = 0; i < arr.length; i++) {
                r+=arr[i];
            }
            System.out.println("r="+r);
        }
    }

    /**
     * Created by yizhenqiang on 18/9/26.
     */
    static class DubboRoundRobinLoadBalance {

        /**
         * 假设该接口有10个可用的Invoker
         */
        private static final int INVOKER_SIZE = 10;
        private static final int[] INVOKER_WEIGHT_ARRAY = new int[]{100, 100, 200, 200, 300, 300, 400, 400, 500, 5000};

        private static final String SERVICE_KEY = "com.test.Test.testMethod";

        private static final ConcurrentMap<String, AtomicPositiveInteger> sequences = new ConcurrentHashMap<String, AtomicPositiveInteger>();

        private static final ConcurrentMap<String, AtomicPositiveInteger> sequences1 = new ConcurrentHashMap<String, AtomicPositiveInteger>();
        private static final ConcurrentMap<String, AtomicPositiveInteger> weightSequences = new ConcurrentHashMap<String, AtomicPositiveInteger>();


        public static void main(String[] args) {
            int times = 1_000_000;
            int[] selectArray = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            long start = System.nanoTime();
            while (times-- > 0) {
                int select = currentSelect();
                selectArray[select]++;
            }

            System.out.println("最新dubbo的RoundRobinLoadBalance耗时：" + (System.nanoTime() - start) / 1000000);
            System.out.println("最新dubbo的RoundRobinLoadBalance流量分布：" + JSON.toJSONString(selectArray));

            times = 1000000;
            selectArray = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            start = System.nanoTime();
            while (times-- > 0) {
                int select = oldSelect();
                selectArray[select]++;
            }

            System.out.println("dubbo-2.5.3的RoundRobinLoadBalance耗时：" + (System.nanoTime() - start) / 1000000);
            System.out.println("dubbo-2.5.3的RoundRobinLoadBalance流量分布：" + JSON.toJSONString(selectArray));

            times = 1000000;
            selectArray = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            start = System.nanoTime();
            while (times-- > 0) {
                int select = oldRandomSelect();
                selectArray[select]++;
            }

            System.out.println("dubbo-2.5.3的RandomLoadBalance耗时：" + (System.nanoTime() - start) / 1000000);
            System.out.println("dubbo-2.5.3的RandomLoadBalance流量分布：" + JSON.toJSONString(selectArray));

        }

        /**
         * 当前最新版本dubbo master分支中实现方式
         *
         * @return 选择的invoker的index
         */
        private static int currentSelect() {
            // 为了测试方便，key默认写死
            String key = SERVICE_KEY;
            // invoker默认是10个
            int length = INVOKER_SIZE; // Number of invokers

            int maxWeight = 0; // The maximum weight
            int minWeight = Integer.MAX_VALUE; // The minimum weight
            final LinkedHashMap<Integer, IntegerWrapper> invokerToWeightMap = new LinkedHashMap<Integer, IntegerWrapper>();
            int weightSum = 0;
            for (int i = 0; i < length; i++) {
                int weight = getWeight(i);
                maxWeight = Math.max(maxWeight, weight); // Choose the maximum weight
                minWeight = Math.min(minWeight, weight); // Choose the minimum weight
                if (weight > 0) {
                    invokerToWeightMap.put(i, new IntegerWrapper(weight));
                    weightSum += weight;
                }
            }
            AtomicPositiveInteger sequence = sequences.get(key);
            if (sequence == null) {
                sequences.putIfAbsent(key, new AtomicPositiveInteger());
                sequence = sequences.get(key);
            }
            int currentSequence = sequence.getAndIncrement();
            if (maxWeight > 0 && minWeight < maxWeight) {
                int mod = currentSequence % weightSum;
                for (int i = 0; i < maxWeight; i++) {
                    for (Map.Entry<Integer, IntegerWrapper> each : invokerToWeightMap.entrySet()) {
                        final Integer k = each.getKey();
                        final IntegerWrapper v = each.getValue();
                        if (mod == 0 && v.getValue() > 0) {
                            return k;
                        }
                        if (v.getValue() > 0) {
                            v.decrement();
                            mod--;
                        }
                    }
                }
            }
            // Round robin
            return currentSequence % length;
        }

        /**
         * 2.5.3版本的roundrobin方式
         *
         * @return
         */
        private static int oldSelect() {
            // 为了测试方便，key默认写死
            String key = SERVICE_KEY;
            // invoker默认是10个
            int length = INVOKER_SIZE; // Number of invokers

            List<Integer> invokers = Lists.newArrayList();

            int maxWeight = 0; // 最大权重
            int minWeight = Integer.MAX_VALUE; // 最小权重
            for (int i = 0; i < length; i++) {
                int weight = getWeight(i);
                maxWeight = Math.max(maxWeight, weight); // 累计最大权重
                minWeight = Math.min(minWeight, weight); // 累计最小权重
            }
            if (maxWeight > 0 && minWeight < maxWeight) { // 权重不一样
                AtomicPositiveInteger weightSequence = weightSequences.get(key);
                if (weightSequence == null) {
                    weightSequences.putIfAbsent(key, new AtomicPositiveInteger());
                    weightSequence = weightSequences.get(key);
                }
                int currentWeight = weightSequence.getAndIncrement() % maxWeight;
                List<Integer> weightInvokers = new ArrayList<Integer>();
                for (int i = 0; i < INVOKER_SIZE; i++) { // 筛选权重大于当前权重基数的Invoker
                    if (getWeight(i) > currentWeight) {
                        weightInvokers.add(i);
                    }
                }
                int weightLength = weightInvokers.size();
                if (weightLength == 1) {
                    return weightInvokers.get(0);
                } else if (weightLength > 1) {
                    invokers = weightInvokers;
                    length = invokers.size();
                }
            }
            AtomicPositiveInteger sequence = sequences1.get(key);
            if (sequence == null) {
                sequences1.putIfAbsent(key, new AtomicPositiveInteger());
                sequence = sequences1.get(key);
            }
            // 取模轮循
            return invokers.get(sequence.getAndIncrement() % length);
        }

        /**
         * 2.5.3版本的random方式
         *
         * @return
         */
        private static int oldRandomSelect() {
            // 为了测试方便，key默认写死
            String key = SERVICE_KEY;
            // invoker默认是10个
            int length = INVOKER_SIZE; // Number of invokers
            int totalWeight = 0; // 总权重

            boolean sameWeight = true; // 权重是否都一样
            for (int i = 0; i < length; i++) {
                int weight = getWeight(i);
                totalWeight += weight; // 累计总权重
                if (sameWeight && i > 0
                        && weight != getWeight(i - 1)) {
                    sameWeight = false; // 计算所有权重是否一样
                }
            }
            if (totalWeight > 0 && !sameWeight) {
                // 如果权重不相同且权重大于0则按总权重数随机
                int offset = ThreadLocalRandom.current().nextInt(totalWeight);
                // 并确定随机值落在哪个片断上
                for (int i = 0; i < length; i++) {
                    //offset -= getWeight(i);
                    //if (offset < 0) {
                    //    return i;
                    //}
                    if (offset < getWeight(i)) {
                        return i;
                    }
                }
            }
            // 如果权重相同或权重为0则均等随机
            return ThreadLocalRandom.current().nextInt(length);
        }

        private static int getWeight(int invokerIndex) {
            return INVOKER_WEIGHT_ARRAY[invokerIndex];
        }

        private static final class IntegerWrapper {
            private int value;

            public IntegerWrapper(int value) {
                this.value = value;
            }

            public int getValue() {
                return value;
            }

            public void setValue(int value) {
                this.value = value;
            }

            public void decrement() {
                this.value--;
            }
        }

    }

}
