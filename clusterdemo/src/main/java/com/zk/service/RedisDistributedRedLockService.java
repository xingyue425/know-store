package com.zk.service;

import com.zk.constant.RedConstant;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisDistributedRedLockService{

    /**
     * redis 客户端
     */
    @Autowired
    private RedissonClient redissonClient;

    /**
     * 获取锁的超时时间
     */
    int acquireTimeout  = 500;

    public boolean lock(String lockKey) {
        RLock redLock = redissonClient.getLock(lockKey);
        boolean isLock;
        try{
            isLock = redLock.tryLock(acquireTimeout, RedConstant.EXPIRED, TimeUnit.MILLISECONDS);
            if(isLock){
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean unlock(String lockKey) {
        RLock redLock=redissonClient.getLock(lockKey);
        if(null != redLock){
            redLock.unlock();
            return true;
        }
        return false;
    }
}
