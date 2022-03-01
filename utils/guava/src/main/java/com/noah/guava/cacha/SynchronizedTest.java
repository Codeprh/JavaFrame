package com.noah.guava.cacha;

import java.util.concurrent.TimeUnit;

public class SynchronizedTest {

    public static void main(String[] args) {
        TicketConsumer ticketConsumer = new TicketConsumer(10);
        Thread why = new Thread(ticketConsumer, "why");
        Thread mx = new Thread(ticketConsumer, "mx");
        why.start();
        mx.start();
    }
}

class TicketConsumer implements Runnable {

    private volatile Integer ticket;

    public TicketConsumer(int ticket) {
        this.ticket = ticket;
    }

    @Override
    public void run() {
        while (true) {
            System.out.println(Thread.currentThread().getName() + "开始抢第" + ticket + "张票，对象加锁之前：" + System.identityHashCode(ticket));
            synchronized (TicketConsumerV2.class) {
                System.out.println(Thread.currentThread().getName() + "抢到第" + ticket + "张票，成功锁到的对象：" + System.identityHashCode(ticket));
                if (ticket > 0) {
                    try {
                        //模拟抢票延迟
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "抢到了第" + ticket-- + "张票，票数减一");
                } else {
                    return;
                }
            }
        }
    }
}
