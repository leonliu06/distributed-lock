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

        try {
            businessService.updateBusinessOrder(new LockEntity("testKey"));
        }catch(Exception e){
            System.out.println(e);
        }

    }


}