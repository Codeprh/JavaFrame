package com.noah.redisson.noahLock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;

import java.io.Closeable;
import java.util.concurrent.TimeUnit;

public class RedisUtils {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    protected static String lockValue = "";

    /**
     * 获取：分布式锁
     *
     * @param key
     * @param exp
     * @return
     */
    public Closeable tryLock(String key, long exp) {
        boolean b = addLock(key, exp);
        if (!b) {
            throw new RuntimeException("get lock fail");
        }
        return () -> releaseLock(key);
    }

    /**
     * 释放：分布式锁
     * @param key
     */
    public void releaseLock(String key) {
        try {
            String currentValue = (String) redisTemplate.opsForValue().get(key);
            if (StringUtils.hasLength(currentValue) && currentValue.equals(lockValue)) {
                redisTemplate.opsForValue().getOperations().delete(key);// 删除key
            }
        } catch (Exception e) {
        }
    }

    /**
     * 原子命令：setNx
     * @param key
     * @param exp
     * @return
     */
    public boolean addLock(String key, long exp) {
        return setNx(key, lockValue, exp);
    }

    /**
     * 原子命令
     *
     * @param key
     * @param value
     * @param time
     * @return
     */
    public boolean setNx(String key, String value, long time) {
        try {
            return redisTemplate.opsForValue().setIfAbsent(key, value, time, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}
