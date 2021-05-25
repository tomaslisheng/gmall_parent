package com.atguigu.gmall.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


@Aspect
@Component
public class GmallCacheAspect {

    @Autowired
    RedisTemplate redisTemplate;

    @Around("@annotation(com.atguigu.gmall.aop.GmallCache)")
    public Object a(ProceedingJoinPoint proceedingJoinPoint) {

        Object result = null;

        // 方法反射信息
        MethodSignature ms = (MethodSignature) proceedingJoinPoint.getSignature();
        GmallCache annotation = ms.getMethod().getAnnotation(GmallCache.class);

        // 方法名
        String name = ms.getMethod().getName();

        // 返回类型
        Class returnType = ms.getReturnType();

        // 参数
        Object[] args = proceedingJoinPoint.getArgs();

        // 前缀后缀
        String redisKey = name;
        if (null != args && args.length > 0) {
            for (Object arg : args) {
                redisKey = redisKey + ":" + arg;
            }
        }

        // 查询缓存
        result = redisTemplate.opsForValue().get(redisKey);

        if (null == result) {
            String lockTag = UUID.randomUUID().toString();
            Boolean ifLock = redisTemplate.opsForValue().setIfAbsent(redisKey + ":lock", lockTag, 1, TimeUnit.SECONDS);// 1秒后如果没有执行完毕，则自动删除锁

            if (ifLock) {
                // 被代理方法
                try {
                    result = proceedingJoinPoint.proceed();//调用被代理方法
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }

                if (null != result) {
                    // 同步到缓存
                    redisTemplate.opsForValue().set(redisKey, result);
                } else {
                    Object o = null;
                    try {
                        o = returnType.newInstance();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }

                    redisTemplate.opsForValue().set(result,o, 10, TimeUnit.SECONDS);
                }
                // 归还同步锁，判断当前的锁值是否是创建时的锁值
//                String currentLockTag = (String)redisTemplate.opsForValue().get("Sku:" + skuId + ":lock");
//                if(!StringUtils.isEmpty(currentLockTag)&&currentLockTag.equals(lockTag)){
//                    redisTemplate.delete("Sku:" + skuId + ":lock");
//                }
                // lua脚本
                String luaScript = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
                DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
                redisScript.setResultType(Long.class);
                redisScript.setScriptText(luaScript);
                redisTemplate.execute(redisScript, Arrays.asList(redisKey + ":lock"), lockTag);
            } else {
                // 自旋
                // 未拿到锁的请求，等待若干秒后，重新访问自己
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // return a(proceedingJoinPoint);
                return redisTemplate.opsForValue().get(redisKey);
            }
        }
        return result;
    }

}
