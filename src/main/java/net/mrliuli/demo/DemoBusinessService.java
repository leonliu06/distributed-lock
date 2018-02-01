package net.mrliuli.demo;

import net.mrliuli.aspect.LockEntity;
import net.mrliuli.aspect.exception.NotGetLockException;
import net.mrliuli.aspect.annotation.LockGuard;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;

/**
 * Created by li.liu on 2018/1/30.
 */

@Service
public class DemoBusinessService {


    @LockGuard(name = "updateBusinessOrder")
    public void updateBusinessOrder1(LockEntity lockEntity) throws NotGetLockException{
        System.out.println("Update Business Order: " + lockEntity.getKey());
        try{
            Thread.sleep(5000);
        }catch (InterruptedException e){
            System.out.println(e);
        }
    }


    /**
     * 内部调用切点方法时（调用者是 this ），为了使得切点方法能够被切面拦截（启用通知），需要获得AOP代理对象，通过代理对象调用，这样才能启用通知。
     * @param lockEntity
     */
    public void updateBusinessOrder2(LockEntity lockEntity){

        DemoBusinessService service;

        try{
            service = (DemoBusinessService)AopContext.currentProxy();
        }catch (IllegalStateException e){
            System.out.println(e);
            service = this;
        }

        try {
            service.updateBusinessOrder1(lockEntity);
        }catch (NotGetLockException e){
            System.out.println(e);
        }

        System.out.println("continue");

    }

}
