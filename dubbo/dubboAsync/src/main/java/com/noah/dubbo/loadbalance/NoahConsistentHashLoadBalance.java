package com.noah.dubbo.loadbalance;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.cluster.loadbalance.AbstractLoadBalance;
import org.apache.dubbo.rpc.support.RpcUtils;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static org.apache.dubbo.common.constants.CommonConstants.COMMA_SPLIT_PATTERN;

@Component
public class NoahConsistentHashLoadBalance extends AbstractLoadBalance {
    public static final String NAME = "noahconsistenthash";

    /**
     * Hash nodes name
     */
    public static final String HASH_NODES = "hash.nodes";

    /**
     * Hash arguments name
     */
    public static final String HASH_ARGUMENTS = "hash.arguments";
    //方法级别，一致性哈希
    private final ConcurrentMap<String, ConsistentHashSelector<?>> selectors = new ConcurrentHashMap<String, ConsistentHashSelector<?>>();

    @SuppressWarnings("unchecked")
    @Override
    protected <T> Invoker<T> doSelect(List<Invoker<T>> invokers, URL url, Invocation invocation) {
        System.out.println("welcome to noahconsistenthash");
        String methodName = RpcUtils.getMethodName(invocation);

        //获取方法级别负载均衡的key
        String key = invokers.get(0).getUrl().getServiceKey() + "." + methodName;

        System.out.println("从selectors中获取value的key=" + key);
        // bug
        //int invokersHashCode = System.identityHashCode(invokers);

        //获取invokers的hashcode
        // using the hashcode of list to compute the hash only pay attention to the elements in the list
        int invokersHashCode = invokers.hashCode();
        //判断方法级别的key，我们是否有
        ConsistentHashSelector<T> selector = (ConsistentHashSelector<T>) selectors.get(key);

        //如果invokers是一个新的list对象，意味着服务提供者的数量发生了变化，伸缩了
        //此时selector == null，第一次调用成立。
        if (selector == null || selector.identityHashCode != invokersHashCode) {

            System.out.println("是新的invokers:" + invokersHashCode + ",原：" + (selector == null ? "null" : selector.identityHashCode));

            //创建新的ConsistentHashSelector，构建函数做了很多事情
            selectors.put(key, new ConsistentHashSelector<T>(invokers, methodName, invokersHashCode));
            selector = (ConsistentHashSelector<T>) selectors.get(key);

            System.out.println("哈希环构建完成，详情如下：");
            for (Map.Entry<Long, Invoker<T>> entry : selector.virtualInvokers.entrySet()) {
                System.out.println("key(哈希值=)" + entry.getKey() + ",value(虚拟节点)=" + entry.getValue());
            }

        }

        //调用ConsistentHashSelector的select方法选择invoker
        return selector.select(invocation);
    }

    private static final class ConsistentHashSelector<T> {

        //使用TreeMap存储Invoker的虚拟节点
        private final TreeMap<Long, Invoker<T>> virtualInvokers;

        //虚拟节点数
        private final int replicaNumber;

        //hashcode
        private final int identityHashCode;

        //请求中的参数下标。需要对请求中对应下标的参数进行哈希计算
        private final int[] argumentIndex;

        ConsistentHashSelector(List<Invoker<T>> invokers, String methodName, int identityHashCode) {
            //treeMap形成的环，依靠treeMap来实现环
            this.virtualInvokers = new TreeMap<Long, Invoker<T>>();
            //该环的hashcode
            this.identityHashCode = identityHashCode;
            URL url = invokers.get(0).getUrl();
            //默认配置的每个node的节点数为160
            this.replicaNumber = url.getMethodParameter(methodName, HASH_NODES, 160);
            //参数hash环计算key的下标，默认是第0位
            String[] index = COMMA_SPLIT_PATTERN.split(url.getMethodParameter(methodName, HASH_ARGUMENTS, "0"));
            argumentIndex = new int[index.length];
            for (int i = 0; i < index.length; i++) {
                argumentIndex[i] = Integer.parseInt(index[i]);
            }

            //循环当前方法提供的invoker
            for (Invoker<T> invoker : invokers) {

                //获取node的ip+port
                String address = invoker.getUrl().getAddress();

                for (int i = 0; i < replicaNumber / 4; i++) {
                    //通过address+编号，计算hash值，16位的数组。
                    byte[] digest = md5(address + i);

                    //每次生成4个虚拟节点
                    for (int h = 0; h < 4; h++) {
                        long m = hash(digest, h);
                        virtualInvokers.put(m, invoker);
                    }
                }
            }
        }

        public Invoker<T> select(Invocation invocation) {
            //选取要一致性hash计算的key
            String key = toKey(invocation.getArguments());
            //md5
            byte[] digest = md5(key);

            return selectForKey(hash(digest, 0));
        }

        /**
         * 选取指定的数组下标的参数，做为hash计算key
         *
         * @param args
         * @return
         */
        private String toKey(Object[] args) {
            StringBuilder buf = new StringBuilder();
            for (int i : argumentIndex) {
                if (i >= 0 && i < args.length) {
                    buf.append(args[i]);
                }
            }
            return buf.toString();
        }

        private Invoker<T> selectForKey(long hash) {
            //通过treeMap的函数，选取顺时针大于等于的第一个节点。
            Map.Entry<Long, Invoker<T>> entry = virtualInvokers.ceilingEntry(hash);

            //如果超过了hash的最大值：选取第一个值，防御性编程
            if (entry == null) {
                entry = virtualInvokers.firstEntry();
            }
            return entry.getValue();
        }

        private long hash(byte[] digest, int number) {
            //&0xFFFFFFFFL含义：环的最大值为Integer.MAX_VALUE
            return (((long) (digest[3 + number * 4] & 0xFF) << 24)
                    | ((long) (digest[2 + number * 4] & 0xFF) << 16)
                    | ((long) (digest[1 + number * 4] & 0xFF) << 8)
                    | (digest[number * 4] & 0xFF))
                    & 0xFFFFFFFFL;
        }

        private byte[] md5(String value) {
            MessageDigest md5;
            try {
                md5 = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
                throw new IllegalStateException(e.getMessage(), e);
            }
            md5.reset();
            byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
            md5.update(bytes);
            return md5.digest();
        }

    }

}
