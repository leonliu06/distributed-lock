package net.mrliuli.lock;

/**
 * Created by li.liu on 2018/1/29.
 */
public interface DistributedLock {

    /**
     * 获取锁
     * @param key
     * @return
     */
    boolean lock(String key);

    /**
     * 释放锁
     * @param key
     */
    void unlock(String key);


}
