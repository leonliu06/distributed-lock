package net.mrliuli.aspect;

import net.mrliuli.aspect.annotation.LockGuard;
import net.mrliuli.config.LockConfigs;
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
    @Around("@annotation(lockGuard) && args(lockEntity, ..)")
    public Object aroundAction(JoinPoint joinPoint, LockGuard lockGuard, LockEntity lockEntity) throws Throwable{

        System.out.println("== Before ((ProceedingJoinPoint)joinPoint).proceed();");

        RedisLock redisLock = new RedisLock(redisTemplate, new LockConfigs(0, 2000));

        boolean got = redisLock.lock(lockEntity.getKey());

        Object ret = null;

        if(got){
            System.out.println("执行业务逻辑");

            ret = ((ProceedingJoinPoint)joinPoint).proceed();

        }else{
            System.out.println("没有得到锁");

            // TODO: 2018/1/30 pointcut method return type

        }

        System.out.println("== After ((ProceedingJoinPoint)joinPoint).proceed();");

        return ret;

    }

}
