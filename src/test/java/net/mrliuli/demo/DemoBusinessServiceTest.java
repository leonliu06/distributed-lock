package net.mrliuli.demo;

import net.mrliuli.BaseTest;
import net.mrliuli.aspect.LockEntity;
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
    public void updateBusinessOrder() throws Exception {

        //DemoBusinessService businessService = new DemoBusinessService();

        LockEntity.LockResult result = businessService.updateBusinessOrder(new LockEntity("testKey"));

        System.out.println("测试是否获得锁：" + result.getSuccess());

    }


}