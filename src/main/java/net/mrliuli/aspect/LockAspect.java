package net.mrliuli.aspect;

import net.mrliuli.aspect.annotation.LockGuard;
import net.mrliuli.config.LockConfigs;
import net.mrliuli.lock.RedisLock;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.support.ClassPathXmlApplicationContext;
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

    StringRedisTemplate redisTemplate = (StringRedisTemplate) (new ClassPathXmlApplicationContext("classpath:context.xml").getBean("redisTemplate"));

    final RedisLock redisLock = new RedisLock(redisTemplate, new LockConfigs(0, 2000));

    /**
     *  Around 通知
     * @param joinPoint
     * @param lockGuard
     * @param lockEntity
     * @throws Throwable
     */
    @Around("@annotation(lockGuard) && args(lockEntity, ..)")
    public void aroundAction(JoinPoint joinPoint, LockGuard lockGuard, LockEntity lockEntity) throws Throwable{

        System.out.println("== Before ((ProceedingJoinPoint)joinPoint).proceed();");

        boolean got = redisLock.lock(lockEntity.getKey());

        if(got){
            System.out.println("执行业务逻辑");
        }

        ((ProceedingJoinPoint)joinPoint).proceed();


        System.out.println("== After ((ProceedingJoinPoint)joinPoint).proceed();");


    }


}
