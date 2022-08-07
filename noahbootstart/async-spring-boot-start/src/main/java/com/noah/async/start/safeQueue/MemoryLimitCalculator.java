package com.noah.async.start.safeQueue;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class MemoryLimitCalculator {

    private static volatile long maxAvailable;

    private static final AtomicBoolean refreshStarted = new AtomicBoolean(false);

    private static void refresh() {
        maxAvailable = Runtime.getRuntime().freeMemory();
    }

    private static void checkAndScheduleRefresh() {
        if (!refreshStarted.get()) {

            //确保比refreshStart被设置为true之前，maxAvailable被赋值了
            refresh();

            //启动刷新线程，线程安全
            if (refreshStarted.compareAndSet(false, true)) {

                ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor(Executors.defaultThreadFactory());
                scheduledExecutorService.scheduleWithFixedDelay(MemoryLimitCalculator::refresh, 50, 50, TimeUnit.MILLISECONDS);

                Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                    refreshStarted.set(false);
                    scheduledExecutorService.shutdown();
                }));
            }
        }
    }

    /**
     * 获取当前最新可用的内存
     *
     * @return
     */
    public static long maxAvailable() {

        checkAndScheduleRefresh();
        return maxAvailable;
    }

    /**
     * 根据百分比计算当前最新可用的内存
     *
     * @param percentage
     * @return
     */
    public static long calculate(final float percentage) {

        if (percentage <= 0 || percentage > 1) {
            throw new IllegalArgumentException();
        }

        checkAndScheduleRefresh();

        return (long) (maxAvailable() * percentage);

    }

    /**
     * 默认可用内存：剩余内存的80%
     *
     * @return
     */
    public static long defaultLimit() {
        checkAndScheduleRefresh();
        return (long) (maxAvailable() * 0.8);
    }
}
