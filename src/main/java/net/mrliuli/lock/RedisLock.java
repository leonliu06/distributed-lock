package net.mrliuli.lock;

import net.mrliuli.config.LockConfigs;
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

    private LockConfigs lockConfigs;

    public RedisLock(){
    }

    public RedisLock(StringRedisTemplate redisTemplate, LockConfigs lockConfigs){
        this.redisTemplate = redisTemplate;
        this.lockConfigs = lockConfigs;
    }

    /**
     * 获取锁，得到锁，则根据配置判断是否设置过期时间并返回true，得不到锁，则根据配置判断是否等待继续尝试获取锁，最后仍得不到则返回false。
     * @param key
     * @return
     */
    @Override
    public boolean lock(String key){

        long start = System.currentTimeMillis();

        while (true){

            // 获取锁成功，返回 true
            if(redisTemplate.opsForValue().setIfAbsent(key, "")){

                // 是否设置过期时间
                if(lockConfigs.getLockExpireMillis() > 0){

                    // Set a timeout on key. After the timeout has expired, the key will automatically be deleted.
                    redisTemplate.expire(key, lockConfigs.getLockExpireMillis(), TimeUnit.MILLISECONDS);

                }

                return true;

            }else {

                //不再等待或等待超时，则获取锁失败，返回 false
                if(start + lockConfigs.getLockWaitingMillis() < System.currentTimeMillis()){
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

    }


    /**
     * 释放锁，如果没有给key设置过期时间，则需要根据业务规则手动释放锁
     * @param key
     */
    @Override
    public void unlock(String key) {

        redisTemplate.delete(key);

    }

}