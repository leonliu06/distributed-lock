package net.mrliuli.demo;

import net.mrliuli.BaseTest;
import net.mrliuli.aspect.LockEntity;
import net.mrliuli.aspect.NotGetLockException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

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

        try {
            businessService.updateBusinessOrder1(new LockEntity("testKey"));
        }catch (NotGetLockException e){
            System.out.println(e.toString());
        }

    }

    @Test
    @Transactional
    @Rollback(false)
    public void updateBusinessOrder2() throws Exception {

        businessService.updateBusinessOrder2(new LockEntity("testKey"));

    }

}