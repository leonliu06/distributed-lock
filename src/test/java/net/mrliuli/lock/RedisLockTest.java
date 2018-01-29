package net.mrliuli.lock;

import junit.framework.TestCase;
import net.mrliuli.config.LockConfigs;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * Created by li.liu on 2018/1/29.
 */
public class RedisLockTest extends TestCase {

    @Test
    public void testLock() throws Exception {

        StringRedisTemplate redisTemplate = (StringRedisTemplate) (new ClassPathXmlApplicationContext("classpath:context.xml").getBean("redisTemplate"));

        final RedisLock redisLock = new RedisLock(redisTemplate, new LockConfigs(0, 2000));

        for(int i = 0; i < 50; i++){
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