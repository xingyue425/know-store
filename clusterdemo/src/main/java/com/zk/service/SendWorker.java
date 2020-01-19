package com.zk.service;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.zk.constant.RedConstant;
import com.zk.util.SpringUtil;
import com.zk.vo.ActivityRemainResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.Callable;

public class SendWorker implements Callable<Boolean> {

    private Logger log= LoggerFactory.getLogger(SendWorker.class);

    private String activityName;
    private long amount;
    private int index;

    public SendWorker(int index,String activityName,long amount){
        this.index=index;
        this.activityName=activityName;
        this.amount=amount;
    }

    @Override
    public Boolean call() {

        BonusService redis= SpringUtil.getBean(BonusService.class);
        RedisTemplateLockService lockService= SpringUtil.getBean(RedisTemplateLockService.class);
        RedisDistributedRedLockService distributedRedLockService= SpringUtil.getBean(RedisDistributedRedLockService.class);

//        int rand= RandomUtil.getRand(100);
        int rand=1;
        System.out.println(String.format("当前线程的名字: %s",Thread.currentThread().getName()));
        try {
            //随机发生异常,记录该异常的名字
            String lockKey=String.format(RedConstant.LOCK_KEY,activityName);
            long m=System.currentTimeMillis()+RedConstant.EXPIRED;
            boolean lockResult=distributedRedLockService.lock(lockKey);
//            boolean lockResult=lockService.lock(lockKey,String.valueOf(m));
            long startTime=System.currentTimeMillis();
            if(lockResult&&(rand%5==0)){
                //发生异常调用
                throw new Exception("发生人为异常");
            }else if(lockResult){
                String key=String.format(RedConstant.ACTIVITY_REMAIN,activityName);
                //对该活动的额度进行扣减
                ActivityRemainResult payResult=redis.payBonus(key,amount);
                if(payResult.isSuccess()){
                    System.out.println(String.format("红包派发索引: %s,派发前余额: %s,派发金额: %s,派发后余额,%s",
                            index,payResult.getPreMoney(),payResult.getChangeMoney(),payResult.getAfterMoney()));
                    return true;
                }else {
                    System.out.println(String.format("红包索引: %s,派发失败原因: %s",index,payResult.getResultCode()));
                }
                long endTime=System.currentTimeMillis();
                System.out.println(String.format("本次请求花费时间: %s ms",(endTime-startTime)));
                distributedRedLockService.unlock(lockKey);
//                lockService.unlock(lockKey,String.valueOf(m));
                Thread.sleep(100);
                return false;
            }else {
                System.out.println(String.format("红包索引: %s,获取锁失败",index));
                return false;
            }
        } catch (InterruptedException e) {
            log.error("扣红包线程发生异常了");
            e.printStackTrace();
        }catch (Exception e){
            log.error("未知异常");
            e.printStackTrace();
        }
        return true;
    }
}
