package net.mrliuli.demo;

import net.mrliuli.aspect.LockEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by li.liu on 2018/1/30.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/context.xml"})
public class DemoBusinessServiceTest {

    @Autowired
    private DemoBusinessService businessService;

    @Test
    public void updateBusinessOrder() throws Exception {

        //DemoBusinessService businessService = new DemoBusinessService();

        businessService.updateBusinessOrder(new LockEntity("testKey"));

    }


}