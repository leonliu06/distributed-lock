package net.mrliuli.lock;

import net.mrliuli.BaseTest;
import net.mrliuli.config.LockConfig;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by li.liu on 2018/1/29.
 */
public class RedisLockTest extends BaseTest{

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    @Transactional
    @Rollback
    public void testLock() {

        final RedisLock redisLock = new RedisLock(redisTemplate, new LockConfig(0, 2000));

        final CountDownLatch countDownLatch = new CountDownLatch(1);

        final int COUNT = 50;
        final CountDownLatch currentThreadLatch = new CountDownLatch(COUNT);

        final List lockList = new ArrayList();

        for(int i = 0; i < COUNT; i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        countDownLatch.await();
                    }catch (InterruptedException e){
                        System.out.println(e);
                    }
                    boolean locked = redisLock.lock("testKey");
                    System.out.println(Thread.currentThread().getName() + "\t" + (locked ? "得到锁" : "未得到锁"));

                    if(locked){ lockList.add(1); }

                    currentThreadLatch.countDown();
                }
            }).start();
        }

        System.out.println("线程准备完毕");
        countDownLatch.countDown();

        try {
            currentThreadLatch.await();
        }catch (InterruptedException e){
            System.out.println(e);
        }

        System.out.println("=================================end");

        System.out.println("释放锁");
        redisLock.unlock("testKey");

        Assert.assertTrue(lockList.size() <= 1);

    }

    public void testUnlock() throws Exception {

    }

}