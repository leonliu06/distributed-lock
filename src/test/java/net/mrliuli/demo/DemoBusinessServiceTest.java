package net.mrliuli.demo;

import net.mrliuli.BaseTest;
import net.mrliuli.aspect.LockEntity;
import net.mrliuli.aspect.exception.NotGetLockException;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by li.liu on 2018/1/30.
 */
public class DemoBusinessServiceTest extends BaseTest {

    @Autowired
    private DemoBusinessService businessService;

    @Test
    @Transactional
    @Rollback(false)
    public void updateBusinessOrder1(){

        int COUNT = 100;

        final CountDownLatch countDownLatch = new CountDownLatch(1);

        final CountDownLatch currentThreadLatch = new CountDownLatch(COUNT);

        final List<NotGetLockException> notGetLockExceptionList = new ArrayList<>();

        for(int i = 0; i < COUNT; i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        countDownLatch.await();
                    }catch (InterruptedException e){
                        System.out.println(e);
                    }
                    LockEntity lockEntity = new LockEntity("testKey");
                    try {
                        System.out.println(Thread.currentThread().getName()+ "\t" + lockEntity.hashCode() + "\t" + "调用业务方法");
                        businessService.updateBusinessOrder1(lockEntity);
                        System.out.println(Thread.currentThread().getName()+ "\t" + lockEntity.hashCode() + "\t" + "得到锁-执行业务方法");
                    }catch (NotGetLockException e){
                        System.out.println(Thread.currentThread().getName()+ "\t" + lockEntity.hashCode() + "\t" + e);
                        notGetLockExceptionList.add(e);
                    }

                    currentThreadLatch.countDown();

                }
            }).start();
        }

        // 这里注意两个 CountDownLatch 的 await() 和 countDown() 的调用顺序，避免出现死锁。
        countDownLatch.countDown();

        try{
            currentThreadLatch.await();
        }catch (InterruptedException e){
            System.out.println(e);
        }

        Assert.assertTrue(notGetLockExceptionList.size() >= COUNT - 1);

    }

    @Test
    @Transactional
    @Rollback(false)
    public void updateBusinessOrder2() throws Exception {

        businessService.updateBusinessOrder2(new LockEntity("testKey"));

    }

}