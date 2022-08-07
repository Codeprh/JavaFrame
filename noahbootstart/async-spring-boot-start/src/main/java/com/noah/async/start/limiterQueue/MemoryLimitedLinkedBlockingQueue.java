package com.noah.async.start.limiterQueue;

import java.lang.instrument.Instrumentation;
import java.util.Collection;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class MemoryLimitedLinkedBlockingQueue<E> extends LinkedBlockingQueue<E> {

    public static final long serialVersionUID = 42L;

    private MemoryLimiter memoryLimiter;

    //构造函数逻辑
    public MemoryLimitedLinkedBlockingQueue(Instrumentation inst) {
        this(inst, Integer.MAX_VALUE);
    }

    public MemoryLimitedLinkedBlockingQueue(Instrumentation inst, long memoryLimit) {
        super(Integer.MAX_VALUE);
        this.memoryLimiter = new MemoryLimiter(inst, memoryLimit);
    }

    public MemoryLimitedLinkedBlockingQueue(Collection<? extends E> c, Instrumentation inst, long memoryLimit) {
        super(c);
        this.memoryLimiter = new MemoryLimiter(inst, memoryLimit);
    }

    /**
     * 设置队列最大容量
     *
     * @param memoryLimit
     */
    public void setMemoryLimiter(long memoryLimit) {
        memoryLimiter.setMemoryLimit(memoryLimit);
    }

    /**
     * 当前已经使用了多少容量
     *
     * @return
     */
    public long getCurrentMemory() {
        return memoryLimiter.getCurrentMemory();
    }

    /**
     * 获取最大容量
     *
     * @return
     */
    public long getMemoryLimit() {

        return memoryLimiter.getMemoryLimit();
    }

    /**
     * 获取剩余容量
     *
     * @return
     */
    public long getRemainingMemory() {
        return memoryLimiter.getCurrentRemainMemory();
    }

    /**
     * Inserts the specified element at the tail of this queue, waiting if
     * necessary for space to become available.
     *
     * @param e
     * @throws InterruptedException {@inheritDoc}
     * @throws NullPointerException {@inheritDoc}
     */
    @Override
    public void put(E e) throws InterruptedException {
        memoryLimiter.acquireInterruptibly(e);
        super.put(e);
    }

    /**
     * Inserts the specified element at the tail of this queue, waiting if
     * necessary up to the specified wait time for space to become available.
     *
     * @param e
     * @param timeout
     * @param unit
     * @return {@code true} if successful, or {@code false} if
     * the specified waiting time elapses before space is available
     * @throws InterruptedException {@inheritDoc}
     * @throws NullPointerException {@inheritDoc}
     */
    @Override
    public boolean offer(E e, long timeout, TimeUnit unit) throws InterruptedException {
        return memoryLimiter.acquire(e, timeout, unit) && super.offer(e, timeout, unit);
    }

    /**
     * Inserts the specified element at the tail of this queue if it is
     * possible to do so immediately without exceeding the queue's capacity,
     * returning {@code true} upon success and {@code false} if this queue
     * is full.
     * When using a capacity-restricted queue, this method is generally
     * preferable to method {@link BlockingQueue#add add}, which can fail to
     * insert an element only by throwing an exception.
     *
     * @param e
     * @throws NullPointerException if the specified element is null
     */
    @Override
    public boolean offer(E e) {
        return memoryLimiter.acquire(e) && super.offer(e);
    }

    @Override
    public E take() throws InterruptedException {

        final E take = super.take();
        memoryLimiter.releaseInterruptibly(take);
        return take;
    }

    @Override
    public E poll(long timeout, TimeUnit unit) throws InterruptedException {
        E poll = super.poll(timeout, unit);
        memoryLimiter.release(poll, timeout, unit);
        return poll;
    }

    @Override
    public E poll() {
        E poll = super.poll();
        memoryLimiter.release(poll);
        return poll;
    }

    /**
     * Removes a single instance of the specified element from this queue,
     * if it is present.  More formally, removes an element {@code e} such
     * that {@code o.equals(e)}, if this queue contains one or more such
     * elements.
     * Returns {@code true} if this queue contained the specified element
     * (or equivalently, if this queue changed as a result of the call).
     *
     * @param o element to be removed from this queue, if present
     * @return {@code true} if this queue changed as a result of the call
     */
    @Override
    public boolean remove(Object o) {
        final boolean remove = super.remove(o);
        if (remove) {
            memoryLimiter.release(o);
        }

        return remove;
    }

    /**
     * Atomically removes all of the elements from this queue.
     * The queue will be empty after this call returns.
     */
    @Override
    public void clear() {
        super.clear();
        memoryLimiter.clear();
    }
}
