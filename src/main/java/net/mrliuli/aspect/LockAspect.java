package net.mrliuli.aspect;

import net.mrliuli.aspect.annotation.LockGuard;
import net.mrliuli.aspect.exception.NotGetLockException;
import net.mrliuli.config.LockConfig;
import net.mrliuli.lock.RedisLock;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(LockAspect.class);

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

        System.out.println(Thread.currentThread().getName() + "\t" + lockEntity.hashCode() + "\t" + "尝试获取锁，== Before ((ProceedingJoinPoint)joinPoint).proceed();");
        RedisLock redisLock = new RedisLock(redisTemplate, new LockConfig(0, 2000));

        Object ret = null;
        
        // 对 “是否获得锁的判断” 保证原子性
        if(redisLock.lock(lockEntity.getKey())){
            try{
                System.out.println(Thread.currentThread().getName() + "\t" + lockEntity.hashCode() + "\t" + "得到锁，执行业务逻辑");
                ret = ((ProceedingJoinPoint)joinPoint).proceed();
            }catch(Throwable e){
                LOGGER.error(Thread.currentThread().getName() + "\t" + lockEntity.hashCode() + "\t" + lockEntity.getKey() + "锁切面的目标方法执行异常");
                e.printStackTrace();
                throw new NotGetLockException("没有得到锁");
            }finally {
                // 得到锁，使用后需要释放
                try{
                    redisLock.unlock(lockEntity.getKey());
                }catch (Exception e){
                    LOGGER.error("unlock {0} error", lockEntity.getKey());
                }
            }
            System.out.println(Thread.currentThread().getName() + "\t" + lockEntity.hashCode() + "\t" + "执行完成，== After ((ProceedingJoinPoint)joinPoint).proceed();");
        }else{
            throw new NotGetLockException(Thread.currentThread().getName() + "\t" + lockEntity.hashCode() + "\t" + "没有得到锁");
        }

        return ret;

    }

}
