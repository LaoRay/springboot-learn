package com.zhengtoon.framework.lock;

/**
 * @author peikaiqiang
 */
public class RedisLockException extends RuntimeException {
    public RedisLockException(String message) {
        super(message);
    }
}
