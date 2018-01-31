package net.mrliuli.demo;

import net.mrliuli.aspect.LockEntity;
import net.mrliuli.aspect.annotation.LockGuard;
import org.springframework.stereotype.Service;

/**
 * Created by li.liu on 2018/1/30.
 */

@Service
public class DemoBusinessService {


    @LockGuard(name = "updateBusinessOrder")
    public LockEntity.LockResult updateBusinessOrder(LockEntity lockEntity){

        System.out.println("Update Business Order: " + lockEntity.getKey());

        return new LockEntity.LockResult(true);

    }


}
