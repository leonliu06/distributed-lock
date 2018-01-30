package net.mrliuli.demo;

import net.mrliuli.aspect.LockEntity;
import net.mrliuli.aspect.annotation.LockGuard;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

/**
 * Created by li.liu on 2018/1/30.
 */

@Service
public class DemoBusinessService {


    @LockGuard(name = "updateBusinessOrder")
    public void updateBusinessOrder(LockEntity lockEntity){

        System.out.println("Update Business Order: " + lockEntity.getKey());


    }


}
