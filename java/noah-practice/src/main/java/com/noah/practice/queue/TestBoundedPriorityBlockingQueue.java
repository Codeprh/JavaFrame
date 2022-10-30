package com.noah.practice.queue;

import java.util.Comparator;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class TestBoundedPriorityBlockingQueue {

    public static void main(String[] args) throws InterruptedException {
        //初始化队列，设置队列的容量为5（只能容纳5个元素），元素类型为integer使用默认比较器，在队列内部将按照从小到大排序
        BoundedPriorityBlockingQueue<Integer> queue = new BoundedPriorityBlockingQueue<Integer>(5);

        //初始化队列，使用自定义的比较器
        queue = new BoundedPriorityBlockingQueue<>(5, new Comparator<Integer>() {

            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        });

        //定义了6个元素，当元素加入到队列中，会按照从小到大排序，当加入第6个元素的时候，队列末尾（最大的元素）将会被抛弃
        int[] array = new int[]{5, 7, 9, 2, 3, 8};
        AtomicInteger c = new AtomicInteger(1);

        BoundedPriorityBlockingQueue<Integer> finalQueue = queue;
        new Thread(() -> {
            while (true) {
                if (c.get() >= 5) {
                    try {
                        Integer take = finalQueue.take();
                        System.out.println("i am take = " + take);
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();


        for (int i : array) {
            queue.offer(i);
            System.out.println("c=" + c.getAndIncrement());
        }

        TimeUnit.SECONDS.sleep(1000);

    }
}
