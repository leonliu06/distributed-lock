package net.mrliuli.aspect;

import net.mrliuli.aspect.annotation.LockGuard;
import net.mrliuli.config.LockConfig;
import net.mrliuli.lock.RedisLock;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by li.liu on 2018/1/30.
 */

/**
 * 声明切面类
 */
@Aspect
@Component
public class LockAspect {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     *  Around 通知
     * @param joinPoint
     * @param lockGuard
     * @param lockEntity
     * @throws Throwable
     */
    @Around("@annotation(lockGuard) && args(lockEntity)")
    public Object aroundAction(JoinPoint joinPoint, LockGuard lockGuard, LockEntity lockEntity) throws Throwable{

        System.out.println("== Before ((ProceedingJoinPoint)joinPoint).proceed();");
        RedisLock redisLock = new RedisLock(redisTemplate, new LockConfig(0, 2000));

        boolean gotLock = false;

        try{

            Object ret;
            gotLock = redisLock.lock(lockEntity.getKey());

            if(gotLock){
                System.out.println("执行业务逻辑");
                ret = ((ProceedingJoinPoint)joinPoint).proceed();
            }else{
                System.out.println("没有得到锁");
                throw new Exception("没有得到锁");
            }

            System.out.println("== After ((ProceedingJoinPoint)joinPoint).proceed();");
            return ret;

        }catch (Throwable e){

            throw new Exception(e);

        }finally {

            // 得到锁，使用后需要释放
            if(gotLock){
                redisLock.unlock(lockEntity.getKey());
            }

        }

    }

}
