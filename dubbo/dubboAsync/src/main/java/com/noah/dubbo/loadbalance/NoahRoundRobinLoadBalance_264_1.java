package com.noah.dubbo.loadbalance;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.utils.AtomicPositiveInteger;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.cluster.loadbalance.AbstractLoadBalance;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
@Component
@Slf4j
public class NoahRoundRobinLoadBalance_264_1 extends AbstractLoadBalance {

    public static final String NAME = "roundrobin";

    private final ConcurrentMap<String, AtomicPositiveInteger> sequences = new ConcurrentHashMap<String, AtomicPositiveInteger>();

    private final ConcurrentMap<String, AtomicPositiveInteger> indexSeqs = new ConcurrentHashMap<String, AtomicPositiveInteger>();

    @Override
    protected <T> Invoker<T> doSelect(List<Invoker<T>> invokers, URL url, Invocation invocation) {
        String key = invokers.get(0).getUrl().getServiceKey() + "." + invocation.getMethodName();
        int length = invokers.size(); // Number of invokers
        int maxWeight = 0; // The maximum weight
        int minWeight = Integer.MAX_VALUE; // The minimum weight
        final List<Invoker<T>> nonZeroWeightedInvokers = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            int weight = NoahLeastActiveLoadBalance.getOriginalWeight(invokers.get(i), invocation);
            maxWeight = Math.max(maxWeight, weight); // Choose the maximum weight
            minWeight = Math.min(minWeight, weight); // Choose the minimum weight
            if (weight > 0) {
                nonZeroWeightedInvokers.add(invokers.get(i));
            }
        }
        AtomicPositiveInteger sequence = sequences.get(key);
        if (sequence == null) {
            sequences.putIfAbsent(key, new AtomicPositiveInteger());
            sequence = sequences.get(key);
        }

        if (maxWeight > 0 && minWeight < maxWeight) {
            AtomicPositiveInteger indexSeq = indexSeqs.get(key);
            if (indexSeq == null) {
                indexSeqs.putIfAbsent(key, new AtomicPositiveInteger(-1));
                indexSeq = indexSeqs.get(key);
            }
            length = nonZeroWeightedInvokers.size();
            AtomicInteger atomicCount=new AtomicInteger(1);

            //每个最大循环次数：invoker.size()
            while (true) {
                int index = indexSeq.incrementAndGet() % length;
                int currentWeight;
                if (index == 0) {
                    currentWeight = sequence.incrementAndGet() % maxWeight;
                    log.info("index:{},需要重新计算,currentWeight:{}", index, currentWeight);
                } else {
                    currentWeight = sequence.get() % maxWeight;
                }

                log.info("index:{},currentWeight:{}", index, currentWeight);
                atomicCount.incrementAndGet();

                if (NoahLeastActiveLoadBalance.getOriginalWeight(nonZeroWeightedInvokers.get(index), invocation) > currentWeight) {
                    log.info("sequences:{},选中的端口:{},循环次数:{}", sequence.get(), nonZeroWeightedInvokers.get(index).getUrl().getPort(), atomicCount.get());
                    log.info("==========over========");
                    return nonZeroWeightedInvokers.get(index);
                }
            }
        }
        // Round robin，所有的invoker权重相同，则进行普通轮训即可
        return invokers.get(sequence.getAndIncrement() % length);
    }
}
