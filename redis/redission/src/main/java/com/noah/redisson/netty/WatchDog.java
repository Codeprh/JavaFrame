package com.noah.redisson.netty;

import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import io.netty.util.TimerTask;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 时间轮栗子:基于定时器实现的
 *
 * @author noah
 */
public class WatchDog {

    public static void main(String[] args) {

        HashedWheelTimer timer = new HashedWheelTimer();

        staticWatchDog(timer);
    }

    private static void staticWatchDog(HashedWheelTimer timer) {
        timer.newTimeout(new TimerTask() {
            @Override
            public void run(Timeout timeout) throws Exception {
                System.out.println("每隔5s" + new Date());
                staticWatchDog(timer);

            }
        }, 5L, TimeUnit.SECONDS);
    }
}
