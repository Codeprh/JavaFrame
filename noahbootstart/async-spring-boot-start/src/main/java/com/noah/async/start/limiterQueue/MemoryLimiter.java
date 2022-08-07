package com.noah.async.start.limiterQueue;

import java.lang.instrument.Instrumentation;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 线程安全的增加/减少【内存使用值】
 * 思想参考{@link java.util.concurrent.LinkedBlockingQueue}
 */
public class MemoryLimiter {

    private final Instrumentation inst;

    private long memoryLimit;
    private final LongAdder memory = new LongAdder();

    private final ReentrantLock acquireLock = new ReentrantLock();
    private final Condition notLimited = acquireLock.newCondition();

    private final ReentrantLock releaseLock = new ReentrantLock();
    private final Condition notEmpty = releaseLock.newCondition();

    public MemoryLimiter(Instrumentation inst) {
        this(inst, Integer.MAX_VALUE);
    }

    public MemoryLimiter(Instrumentation inst, long memoryLimit) {
        if (memoryLimit <= 0) {
            throw new IllegalArgumentException();
        }
        this.inst = inst;
        this.memoryLimit = memoryLimit;
    }

    public void setMemoryLimit(long memoryLimit) {

        if (memoryLimit <= 0) {
            throw new IllegalArgumentException();
        }

        this.memoryLimit = memoryLimit;
    }

    public long getMemoryLimit() {
        return memoryLimit;
    }

    /**
     * 获取当前的使用内存
     *
     * @return
     */
    public long getCurrentMemory() {
        return memory.sum();
    }

    /**
     * 获取当前剩余的内存
     *
     * @return
     */
    public long getCurrentRemainMemory() {
        return getMemoryLimit() - getCurrentMemory();
    }

    private void signalNotLimited() {
        acquireLock.lock();
        try {
            notLimited.signal();
        } finally {
            acquireLock.unlock();
        }
    }

    private void signalNotEmpty() {
        releaseLock.lock();
        try {
            notEmpty.signal();
        } finally {
            releaseLock.unlock();
        }

    }

    private void fullyLock() {
        acquireLock.lock();
        releaseLock.lock();
    }

    private void fullyUnLock() {
        acquireLock.unlock();
        releaseLock.unlock();
    }

    /**
     * 非阻塞的增加【内存使用值】
     *
     * @param e
     * @return
     */
    public boolean acquire(Object e) {
        if (e == null) {
            throw new NullPointerException();
        }

        if (getCurrentRemainMemory() <= 0) {
            return false;
        }

        acquireLock.lock();
        try {

            final long sum = getCurrentMemory();
            final long objectSize = inst.getObjectSize(e);

            if (sum + objectSize >= getMemoryLimit()) {
                return false;
            }

            memory.add(objectSize);
            if (memory.sum() < memoryLimit) {
                notLimited.signal();
            }

        } finally {
            acquireLock.unlock();
        }

        if (memory.sum() > 0) {
            signalNotEmpty();
        }

        return true;
    }

    /**
     * 阻塞的增加【内存使用值】
     *
     * @param e
     * @throws InterruptedException
     */
    public void acquireInterruptibly(Object e) throws InterruptedException {

        if (e == null) {
            throw new NullPointerException();
        }

        acquireLock.lockInterruptibly();

        try {
            final long objectSize = inst.getObjectSize(e);

            while (memory.sum() + objectSize >= getMemoryLimit()) {
                notLimited.await();
            }

            memory.add(objectSize);
            if (memory.sum() < getMemoryLimit()) {
                notLimited.signal();
            }


        } finally {
            acquireLock.unlock();
        }

        if (memory.sum() > 0) {
            signalNotEmpty();
        }
    }

    /**
     * 等待指定时间，增加【内存使用值】
     *
     * @param e
     * @param timeout
     * @param unit
     * @return
     */
    public boolean acquire(Object e, long timeout, TimeUnit unit) throws InterruptedException {

        if (Objects.isNull(e)) {
            throw new NullPointerException();
        }

        long nanos = unit.toNanos(timeout);

        acquireLock.lockInterruptibly();

        try {
            final long objectSize = inst.getObjectSize(e);
            while (memory.sum() + objectSize >= getMemoryLimit()) {
                if (nanos <= 0) {
                    return false;
                }
                nanos = notLimited.awaitNanos(nanos);
            }

            memory.add(objectSize);
            if (memory.sum() < memoryLimit) {
                notLimited.signal();
            }
        } finally {
            acquireLock.unlock();
        }

        if (memory.sum() > 0) {
            signalNotEmpty();
        }
        return true;

    }

    /**
     * 非阻塞的减少【内存使用值】
     *
     * @param e
     */
    public void release(Object e) {

        if (Objects.isNull(e)) {
            return;
        }

        if (memory.sum() == 0) {
            return;
        }

        releaseLock.lock();
        try {

            final long objectSize = inst.getObjectSize(e);

            if (memory.sum() > 0) {
                memory.add(-objectSize);
            }

            if (memory.sum() > 0) {
                signalNotEmpty();
            }

        } finally {
            releaseLock.unlock();
        }

        if (memory.sum() < getMemoryLimit()) {
            signalNotLimited();
        }

    }

    /**
     * 阻塞的减少【内存使用值】
     *
     * @param e
     */
    public void releaseInterruptibly(Object e) throws InterruptedException {

        if (Objects.isNull(e)) {
            return;
        }

        releaseLock.lockInterruptibly();

        try {

            final long objectSize = inst.getObjectSize(e);
            if (getCurrentMemory() == 0) {
                notEmpty.await();
            }

            memory.add(-objectSize);
            if (memory.sum() > 0) {
                notEmpty.signal();
            }

        } finally {
            releaseLock.unlock();
        }

        if (memory.sum() < getMemoryLimit()) {
            signalNotLimited();
        }

    }

    public void release(Object e, long timeout, TimeUnit unit) throws InterruptedException {

        long nanos = unit.toNanos(timeout);
        releaseLock.lock();

        try {

            while (memory.sum() == 0) {
                if (nanos <= 0) {
                    return;
                }
                nanos = notEmpty.awaitNanos(nanos);
            }

            final long objectSize = inst.getObjectSize(e);

            memory.add(-objectSize);

            if (memory.sum() > 0) {
                notEmpty.signal();
            }

        } finally {
            releaseLock.unlock();
        }

        if (memory.sum() < getMemoryLimit()) {
            signalNotLimited();
        }
    }

    public void clear() {

        fullyLock();

        try {
            if (memory.sumThenReset() < memoryLimit) {
                notLimited.signal();
            }
        } finally {
            fullyUnLock();

        }
    }


}
