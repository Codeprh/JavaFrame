package com.noah.guava.cacha;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class SynchronizedSafeTest {

    public static void main(String[] args) {
        TicketConsumerV2 consumerV2 = new TicketConsumerV2();
        new Thread(consumerV2,"noah").start();
        new Thread(consumerV2,"luffy").start();

    }

}
/**
 * 抢票进程
 */
@Slf4j
class TicketConsumerV2 implements Runnable {

    private volatile Integer ticket = 10;

    @SneakyThrows
    @Override
    public void run() {
        while (true) {

            log.info("threadName:{},开始抢:{}张票", Thread.currentThread().getName(), ticket);

            synchronized (ticket) {

                log.info("threadName:{},抢到:{}张票,锁成功", Thread.currentThread().getName(), ticket);

                if (ticket > 0) {
                    TimeUnit.SECONDS.sleep(1);
                    log.info("threadName:{},抢到:{}张票,抢成功", Thread.currentThread().getName(), ticket);
                } else {
                    return;
                }

            }

        }
    }
}
