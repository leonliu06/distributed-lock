package net.mrliuli.lock;

import net.mrliuli.BaseTest;
import net.mrliuli.config.LockConfig;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by li.liu on 2018/1/29.
 */
public class RedisLockTest extends BaseTest{

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    @Transactional
    @Rollback(false)
    public void testLock() throws Exception {

        final RedisLock redisLock = new RedisLock(redisTemplate, new LockConfig(0, 2000));

        for(int i = 0; i < 10; i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    boolean locked = redisLock.lock("testKey");
                    System.out.println(Thread.currentThread().getName() + (locked ? "获得了锁" : "没有获得锁"));
                    //redisLock.unlock("testKey");
                }
            }).start();
        }

        Thread.sleep(10000);

    }

    public void testUnlock() throws Exception {

    }

}