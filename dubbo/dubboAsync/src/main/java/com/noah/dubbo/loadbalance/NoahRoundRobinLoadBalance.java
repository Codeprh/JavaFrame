package com.noah.dubbo.loadbalance;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.cluster.loadbalance.AbstractLoadBalance;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

public class NoahRoundRobinLoadBalance extends AbstractLoadBalance {

    public static final String NAME = "roundrobin";

    private static final int RECYCLE_PERIOD = 60000;

    protected static class WeightedRoundRobin {
        // 服务提供者权重
        private int weight;
        // 当前权重
        private AtomicLong current = new AtomicLong(0);
        // 最后一次更新时间
        private long lastUpdate;

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
            // 初始值为0
            current.set(0);
        }

        public long increaseCurrent() {
            // CAS操作 current = current + weight
            return current.addAndGet(weight);
        }

        public void sel(int total) {
            // CAS操作 current = current - total
            current.addAndGet(-1 * total);
        }

        public long getLastUpdate() {
            return lastUpdate;
        }

        public void setLastUpdate(long lastUpdate) {
            this.lastUpdate = lastUpdate;
        }
    }

    private ConcurrentMap<String, ConcurrentMap<String, WeightedRoundRobin>> methodWeightMap = new ConcurrentHashMap<String, ConcurrentMap<String, WeightedRoundRobin>>();

    /**
     * get invoker addr list cached for specified invocation
     * <p>
     * <b>for unit test only</b>
     *
     * @param invokers
     * @param invocation
     * @return
     */
    protected <T> Collection<String> getInvokerAddrList(List<Invoker<T>> invokers, Invocation invocation) {
        String key = invokers.get(0).getUrl().getServiceKey() + "." + invocation.getMethodName();
        Map<String, WeightedRoundRobin> map = methodWeightMap.get(key);
        if (map != null) {
            return map.keySet();
        }
        return null;
    }

    @Override
    protected <T> Invoker<T> doSelect(List<Invoker<T>> invokers, URL url, Invocation invocation) {
        // 获取url到weightRoundRobin映射表，如果为空，则创建一个新的
        String key = invokers.get(0).getUrl().getServiceKey() + "." + invocation.getMethodName();
        ConcurrentMap<String, WeightedRoundRobin> map = methodWeightMap.computeIfAbsent(key, k -> new ConcurrentHashMap<>());
        int totalWeight = 0;
        long maxCurrent = Long.MIN_VALUE;
        long now = System.currentTimeMillis();
        Invoker<T> selectedInvoker = null;
        WeightedRoundRobin selectedWRR = null;
        for (Invoker<T> invoker : invokers) {
            String identifyString = invoker.getUrl().toIdentityString();
            int weight = NoahLeastActiveLoadBalance.getWeight(invoker, invocation);
            //检测当前Invoker是否有相应的WeightedRoundRobin,没有则创建
            WeightedRoundRobin weightedRoundRobin = map.computeIfAbsent(identifyString, k -> {
                WeightedRoundRobin wrr = new WeightedRoundRobin();
                // 设置Invoker 权重
                wrr.setWeight(weight);
                return wrr;
            });
            // Invoker 权重不等于weightedRoundRobin中保存的权重，说明权重变化，进行更新
            if (weight != weightedRoundRobin.getWeight()) {
                //weight changed
                weightedRoundRobin.setWeight(weight);
            }
            // CAS操作 cur = current+weight
            long cur = weightedRoundRobin.increaseCurrent();
            // 设置 lastUpdate，表示近期更新过
            weightedRoundRobin.setLastUpdate(now);
            if (cur > maxCurrent) {
                maxCurrent = cur;
                // 将具有最大current 权重的Invoker赋值给selectedInvoker
                selectedInvoker = invoker;
                // 将Invoker对应的weightedRoundRobin赋值给selectedWRR，后面用到
                selectedWRR = weightedRoundRobin;
            }
            //计算权重之和
            totalWeight += weight;
        }
        // 如果Invoker个数与ConcurrentMap<String, WeightedRoundRobin>的key个数不一致
        if (invokers.size() != map.size()) {
            //去除长时间未被更新的节点（60s）
            map.entrySet().removeIf(item -> now - item.getValue().getLastUpdate() > RECYCLE_PERIOD);
        }
        if (selectedInvoker != null) {
            // CAS操作，将最大权重的Invoker减去权重总和
            selectedWRR.sel(totalWeight);
            // 返回具有最大current的Invoker
            return selectedInvoker;
        }
        // should not happen here
        return invokers.get(0);
    }}
