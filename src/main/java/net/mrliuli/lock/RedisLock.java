package net.mrliuli.lock;

import net.mrliuli.config.LockConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * Created by li.liu on 2018/1/29.
 */
public class RedisLock implements DistributedLock {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisLock.class);

    private StringRedisTemplate redisTemplate;

    private LockConfig lockConfig;

    public RedisLock(){
    }

    public RedisLock(StringRedisTemplate redisTemplate, LockConfig lockConfig){
        this.redisTemplate = redisTemplate;
        this.lockConfig = lockConfig;
    }

    /**
     * 获取锁，得到锁，则根据配置判断是否设置过期时间并返回true，得不到锁，则根据配置判断是否等待继续尝试获取锁，最后仍得不到则返回false。
     * @param key
     * @return
     */
    @Override
    public boolean lock(String key){

        LOGGER.info("start to execute RedisLock lock({0}), threadName: {1}", key, Thread.currentThread().getName());

        long start = System.currentTimeMillis();

        try {
            while (true){

                // 获取锁成功，返回 true
                if(redisTemplate.opsForValue().setIfAbsent(key, "")){

                    // 是否设置过期时间
                    if(lockConfig.getLockExpireMillis() > 0){

                        // Set a timeout on key. After the timeout has expired, the key will automatically be deleted.
                        redisTemplate.expire(key, lockConfig.getLockExpireMillis(), TimeUnit.MILLISECONDS);

                    }

                    return true;

                }else {

                    //不再等待或等待超时，则获取锁失败，返回 false
                    if(start + lockConfig.getLockWaitingMillis() < System.currentTimeMillis()){
                        return false;
                    }

                    try{
                        Thread.sleep(50);
                    }catch (InterruptedException e){
                        LOGGER.error(e.getMessage());
                        return false;
                    }

                }
            }
        }catch (Exception e){
            LOGGER.error("execute RedisLock lock({0}) error!", key);
            return false;
        }

    }


    /**
     * 释放锁，如果没有给key设置过期时间，则需要根据业务规则手动释放锁
     * @param key
     */
    @Override
    public void unlock(String key) {

        LOGGER.info("start to execute RedisLock unlock({0}), threadName: {1}", key, Thread.currentThread().getName());

        try {
            redisTemplate.delete(key);
        }catch (Exception e){
            LOGGER.error("execute RedisLock unlock({0}) occur unknown error, threadName: {1}", key, Thread.currentThread().getName());
        }

    }

}
