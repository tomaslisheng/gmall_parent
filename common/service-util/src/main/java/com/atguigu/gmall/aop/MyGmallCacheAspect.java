package com.atguigu.gmall.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * author lisheng
 * Date:2021/5/22
 * Description: redis 缓存公共方法
 */
@Component
@Aspect
public class MyGmallCacheAspect {

    @Autowired
    public RedisTemplate redisTemplate;

    //@Around("@annotation(com.atguigu.gmall.aop.GmallCache)")
    public Object methodAll(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object result = null;
        //获取方法的反射信息
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        GmallCache annotation = signature.getMethod().getAnnotation(GmallCache.class);
        //获取方法名
        String name = signature.getMethod().getName();
        //获取返回类型
        Class returnType = signature.getReturnType();
        //获取参数
        Object[] args = proceedingJoinPoint.getArgs();

        String redisKey = name;
        //拼接redis 存储值的key
        if(args!=null&&args.length>0){
            for (Object arg : args) {
                redisKey=redisKey+":"+arg;
            }
        }
        //根据key查询redis缓存
        result = redisTemplate.opsForValue().get(redisKey);
        //如果缓存中没有数据
        if(result == null){
            String lockTag = UUID.randomUUID().toString();
            //由于考虑并发情况，需要添加reids分布式锁，来实现限流的作用
            Boolean aBoolean = redisTemplate.opsForValue().setIfAbsent(redisKey+ ":lock", lockTag,1, TimeUnit.SECONDS);
            //拿到锁，执行查询操作。否则等待
            if(aBoolean){
                //调用被代理的方法
                 result = proceedingJoinPoint.proceed();
                 //如果从MySQL查询有值，则缓存
                 if (result!=null){
                     redisTemplate.opsForValue().set(redisKey,result);
                 }
                 //否则 给一个虚拟值，并且给一个过期时间。短时间内不让重新查询
                else {
                     Object o = returnType.newInstance();
                     redisTemplate.opsForValue().set(redisKey,o,10,TimeUnit.SECONDS);
                 }
               /* String concurrentTag = (String) redisTemplate.opsForValue().get("sku:" + skuId + "lock");
                if(concurrentTag!=null&&concurrentTag.equals(lockTag)){
                    redisTemplate.delete("sku:" + skuId + "lock");
                }*/
               //lua脚本
                String luaScript = "if redis.call('get',KEYS[1])==ARGV[1] then return redis.call('del',KEYS[1]";
                DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
                redisScript.setResultType(Long.class);
                redisScript.setScriptText(luaScript);
                redisTemplate.execute(redisScript, Arrays.asList(redisKey+":lock"),lockTag);
            }else{
                //如果没有获取锁，则等待3秒，重新查询
                Thread.sleep(3000);
                return  redisTemplate.opsForValue().get(redisKey);
            }
        }
        return result;
    }
}
