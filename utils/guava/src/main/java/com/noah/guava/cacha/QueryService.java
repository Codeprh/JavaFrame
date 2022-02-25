package com.noah.guava.cacha;

import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.*;

@Slf4j
public class QueryService {

    private final Map<String, Integer> queryMap = new HashMap<>();
    private final Map<String, Integer> concurrentHashMap = new ConcurrentHashMap<>();
    @Getter
    private final Map<String, Future<Integer>> futureConcurrentHashMap = new ConcurrentHashMap<>();

    /**
     * 有问题的查询：线get，再put请求
     *
     * @param name
     * @return
     */
    public Integer query(String name) {

        Integer num = queryMap.get(name);

        if (Objects.isNull(num)) {
            num = loadFromDb(name);
            queryMap.put(name, num);
        }

        return num;
    }

    /**
     * 有问题的查询：线get，再put请求
     * 但是变更为使用CHM
     *
     * @param name
     * @return
     */
    public Integer queryWithCHM(String name) {

        //如果key不存在，去取并且存入到map。存在，覆盖或者别的操作
        //return concurrentHashMap.compute(name, (k, v) -> Objects.isNull(v) ? loadFromDb(k) : v);

        //如果key不存在，去取并且存入到map。存在，直接返回
        return concurrentHashMap.computeIfAbsent(name, k -> loadFromDb(k));

        //下面非线程安全方法，多步操作非原子性

        //Integer num = concurrentHashMap.get(name);
        //
        //if (Objects.isNull(num)) {
        //    num = loadFromDb(name);
        //    concurrentHashMap.put(name, num);
        //}
        //
        //return num;

        //return null;
    }

    /**
     * 使用future的CHM方式
     *
     * @param name
     * @return
     */
    @SneakyThrows
    public Integer queryWithFCHM(String name) {

        //线程安全，原子性操作
        Future<Integer> integerFuture = futureConcurrentHashMap.computeIfAbsent(name, k -> {
            FutureTask<Integer> futureTask = new FutureTask<>(new Callable<Integer>() {
                @Override
                public Integer call() {
                    return loadFromDb(name);
                }
            });
            futureTask.run();
            return futureTask;
        });

        return integerFuture.get();

        //多步操作，非原子性
        //Future<Integer> future = futureConcurrentHashMap.get(name);
        //
        //if (Objects.isNull(future)) {
        //
        //    FutureTask<Integer> futureTask = new FutureTask<Integer>(new Callable<Integer>() {
        //        @Override
        //        public Integer call() {
        //            return loadFromDb(name);
        //        }
        //    });
        //
        //    future = futureTask;
        //    futureConcurrentHashMap.put(name,futureTask);
        //    futureTask.run();
        //}
        //
        //return future.get();
    }


    public Integer queryWithFCCHM(String name) {
        while (true) {

            Future<Integer> future = futureConcurrentHashMap.get(name);

            if (Objects.isNull(future)) {

                FutureTask<Integer> futureTask = new FutureTask<Integer>(new Callable<Integer>() {
                    @Override
                    public Integer call() {
                        return loadFromDb(name);
                    }
                });

                //说明map没有本次查询
                future = futureConcurrentHashMap.putIfAbsent(name, futureTask);
                if (Objects.isNull(future)) {
                    future = futureTask;
                    futureTask.run();
                }
            }

            try {
                return future.get();
            } catch (CancellationException e) {
                log.info("某个任务，被移除，name:{}", name);
                futureConcurrentHashMap.remove(name,future);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 加重量级锁解决：锁整个方法，等价串行化了
     *
     * @param name
     * @return
     */
    public synchronized Integer queryWithSync(String name) {

        Integer num = queryMap.get(name);

        if (Objects.isNull(num)) {
            num = loadFromDb(name);
            queryMap.put(name, num);
        }

        return num;
    }

    /**
     * 加重量级锁解决：锁单个用户
     *
     * @param name
     * @return
     */
    public Integer queryWithSyncLess(String name) {

        Integer num = queryMap.get(name);

        if (Objects.isNull(num)) {
            synchronized (name) {
                num = loadFromDb(name);
                queryMap.put(name, num);
            }
        }

        return num;
    }


    @SneakyThrows
    private Integer loadFromDb(String name) {

        log.info("{},开始从db查成绩了～～", name);
        TimeUnit.SECONDS.sleep(1);

        return ThreadLocalRandom.current().nextInt(400, 500);
    }
}
