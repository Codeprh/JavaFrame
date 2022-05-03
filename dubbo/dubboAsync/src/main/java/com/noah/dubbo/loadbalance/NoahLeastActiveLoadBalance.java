package com.noah.dubbo.loadbalance;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.RpcStatus;
import org.apache.dubbo.rpc.cluster.Constants;
import org.apache.dubbo.rpc.cluster.loadbalance.AbstractLoadBalance;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

import static org.apache.dubbo.common.constants.CommonConstants.TIMESTAMP_KEY;
import static org.apache.dubbo.common.constants.RegistryConstants.REGISTRY_KEY;
import static org.apache.dubbo.common.constants.RegistryConstants.REGISTRY_SERVICE_REFERENCE_PATH;
import static org.apache.dubbo.rpc.cluster.Constants.*;

/**
 * LeastActiveLoadBalance，客户端的负载均衡
 * dubbo2.6.0版本的实现
 * <p>
 * {@link LeastActiveLoadBalance}
 */
@Component
public class NoahLeastActiveLoadBalance extends AbstractLoadBalance {

    public static final String NAME = "leastactive";

    private final Random random = new Random();

    @Override
    protected <T> Invoker<T> doSelect(List<Invoker<T>> invokers, org.apache.dubbo.common.URL url, Invocation invocation) {

        //服务提供者数量
        int length = invokers.size(); // Number of invokers

        //最小活跃次数，活跃次数一定是>=0的数，设置为-1，就一定会被if给替换了
        int leastActive = -1; // The least active value of all invokers

        //具有相同最小活跃次数的invoker的数量
        int leastCount = 0; // The number of invokers having the same least active value (leastActive)

        //具有相同最小活跃数的invoker数组下标
        int[] leastIndexs = new int[length]; // The index of invokers having the same least active value (leastActive)

        //最小活跃数的权重之和
        int totalWeight = 0; // The sum of weights

        //记录最小活跃数的权重，和判断具有相同的最小活跃数的权重是否相同
        int firstWeight = 0; // Initial value, used for comparision

        //具有相同最小活跃数的invoker，权重是否相同
        boolean sameWeight = true; // Every invoker has the same weight value?

        //遍历invoker列表，获取当前连接数和配置的权重
        for (int i = 0; i < length; i++) {

            Invoker<T> invoker = invokers.get(i);

            //问题0：active为什么返回0
            //返回的是invoker的活跃数
            int active = RpcStatus.getStatus(invoker.getUrl(), invocation.getMethodName()).getActive(); // Active number

            //问题1：1-1：应该要返回预热后的权重。1-2：不应该重复计算，使用weight[]来存储
            int weight = invoker.getUrl().getMethodParameter(invocation.getMethodName(), Constants.WEIGHT_KEY, Constants.DEFAULT_WEIGHT); // Weight


            if (leastActive == -1 || active < leastActive) { // Restart, when find a invoker having smaller least active value.

                //初始化或者找到一个活跃数更小invoker
                leastActive = active; // Record the current least active value
                leastCount = 1; // Reset leastCount, count again based on current leastCount
                leastIndexs[0] = i; // Reset
                totalWeight = weight; // Reset
                firstWeight = weight; // Record the weight the first invoker
                sameWeight = true; // Reset, every invoker has the same weight value?
            } else if (active == leastActive) { // If current invoker's active value equals with leaseActive, then accumulating.

                //具有相同活跃数的invoker，累加他们的权重
                leastIndexs[leastCount++] = i; // Record index number of this invoker
                totalWeight += weight; // Add this invoker's weight to totalWeight.

                //问题2：优化判断i>0
                // If every invoker has the same weight?
                if (sameWeight && i > 0 && weight != firstWeight) {
                    sameWeight = false;
                }
            }
        }

        //最小活跃数invoker只有一个，直接返回
        // assert(leastCount > 0)
        if (leastCount == 1) {
            // If we got exactly one invoker having the least active value, return this invoker directly.
            return invokers.get(leastIndexs[0]);
        }

        //加权随机实现：具备相同最小活跃数的不同权重invoker列表获取
        if (!sameWeight && totalWeight > 0) {

            //问题3：使用ThreadLocalRandom.current()替换
            // If (not every invoker has the same weight & at least one invoker's weight>0), select randomly based on totalWeight.
            int offsetWeight = random.nextInt(totalWeight);

            // Return a invoker based on the random value.
            for (int i = 0; i < leastCount; i++) {

                int leastIndex = leastIndexs[i];
                //等价问题1
                offsetWeight -= getWeight(invokers.get(leastIndex), invocation);

                //问题4：<=0可能没有遍历完数组就返回了，导致最后一个invoker没有机会被选到。栗子：5，2，1。offsetWeight=7
                if (offsetWeight <= 0)
                    return invokers.get(leastIndex);
            }
        }

        //随机返回一个invoker
        // If all invokers have the same weight value or totalWeight=0, return evenly.
        return invokers.get(leastIndexs[random.nextInt(leastCount)]);
    }

    /**
     * 获取预热后invoker的权重
     *
     * @param invoker
     * @param invocation
     * @return
     */
    int getWeight(Invoker<?> invoker, Invocation invocation) {
        int weight;
        URL url = invoker.getUrl();
        // Multiple registry scenario, load balance among multiple registries.
        if (REGISTRY_SERVICE_REFERENCE_PATH.equals(url.getServiceInterface())) {
            weight = url.getParameter(REGISTRY_KEY + "." + WEIGHT_KEY, DEFAULT_WEIGHT);
        } else {
            weight = url.getMethodParameter(invocation.getMethodName(), WEIGHT_KEY, DEFAULT_WEIGHT);
            if (weight > 0) {

                //获取启动时间戳
                long timestamp = invoker.getUrl().getParameter(TIMESTAMP_KEY, 0L);

                if (timestamp > 0L) {

                    //获取应用启动了多久
                    long uptime = System.currentTimeMillis() - timestamp;
                    if (uptime < 0) {
                        return 1;
                    }

                    //获取预热时间：默认值为10min
                    int warmup = invoker.getUrl().getParameter(WARMUP_KEY, DEFAULT_WARMUP);

                    //启动时间<预热时间，重新计算权重
                    if (uptime > 0 && uptime < warmup) {
                        weight = calculateWarmupWeight((int) uptime, warmup, weight);
                    }
                }
            }
        }
        return Math.max(weight, 0);
    }

    /**
     * 获取还没到预热时间的权重
     *
     * @param uptime
     * @param warmup
     * @param weight
     * @return
     */
    static int calculateWarmupWeight(int uptime, int warmup, int weight) {
        //除于一个数等于乘以一个数的倒数，所以：(uptime/warmup)*weight
        int ww = (int) (uptime / ((float) warmup / weight));
        return ww < 1 ? 1 : (Math.min(ww, weight));
    }
}
