package net.mrliuli.aspect;

import net.mrliuli.aspect.annotation.LockGuard;
import net.mrliuli.aspect.exception.NotGetLockException;
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
    public Object aroundAction(JoinPoint joinPoint, LockGuard lockGuard, LockEntity lockEntity) throws NotGetLockException {

        System.out.println("== Before ((ProceedingJoinPoint)joinPoint).proceed();");
        RedisLock redisLock = new RedisLock(redisTemplate, new LockConfig(0, 2000));

        Object ret = null;
        
        // 对 “是否获得锁的判断” 保证原子性
        if(redisLock.lock(lockEntity.getKey())){
            try{
                System.out.println("执行业务逻辑");
                ret = ((ProceedingJoinPoint)joinPoint).proceed();
            }catch(Throwable e){
                System.out.println("锁切面的目标方法执行异常");
                e.printStackTrace();
                throw new NotGetLockException("没有得到锁");
            }finally {
                // 得到锁，使用后需要释放
                redisLock.unlock(lockEntity.getKey());
            }
            System.out.println("== After ((ProceedingJoinPoint)joinPoint).proceed();");
        }else{
            System.out.println("没有得到锁");
            throw new NotGetLockException("没有得到锁");
        }

        return ret;

    }

}
