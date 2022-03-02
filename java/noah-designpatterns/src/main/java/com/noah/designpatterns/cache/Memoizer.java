package com.noah.designpatterns.cache;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.*;

/**
 * 装饰器类：高效且可伸缩的结果缓存
 *
 * @param <A>
 * @param <V>
 */
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class Memoizer<A, V> implements Computable<A, V> {

    /**
     * 缓存map
     */
    private final Map<A, Future<V>> cache = new ConcurrentHashMap<A, Future<V>>();
    /**
     * 包装器：真实执行业务
     */
    private Computable<A, V> computable;

    @Override
    public V compute(A arg) throws Exception {

        {
            while (true) {

                Future<V> future = cache.get(arg);

                if (Objects.isNull(future)) {

                    FutureTask<V> futureTask = new FutureTask<V>(new Callable<V>() {
                        @SneakyThrows
                        @Override
                        public V call() {
                            return computable.compute(arg);
                        }
                    });

                    //说明map没有本次查询
                    future = cache.putIfAbsent(arg, futureTask);
                    if (Objects.isNull(future)) {
                        future = futureTask;
                        futureTask.run();
                    }
                }

                try {
                    return future.get();
                } catch (CancellationException e) {
                    log.info("某个任务，被移除，arg:{}", arg);
                    cache.remove(arg, future);
                } catch (ExecutionException e) {
                    log.error("装饰器报错", e);
                }
            }
        }
    }
}
