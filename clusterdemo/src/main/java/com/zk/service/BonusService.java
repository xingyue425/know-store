package com.zk.service;

import com.zk.constant.RedConstant;
import com.zk.vo.ActivityRemainResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class BonusService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /*
     * 对活动的余额进行扣减
     * @param key 活动余额对应的key
     * @param money 需要扣减的金额
     */
    public ActivityRemainResult payBonus(String key, long money){

        ActivityRemainResult result=new ActivityRemainResult();
        //设置默认初始化信息
        result.setResultCode(ActivityRemainResult.SUCCESS);
        result.setSuccess(true);
        try {
            Long activityRemain=null;
            Object value=redisTemplate.opsForValue().get(key);
            if(value==null){
                //获取活动余额信息,这里写死
                activityRemain= RedConstant.MONEY;
            }else{
                activityRemain=Long.valueOf(value.toString());
            }
            //校验是不是够该红包
            if(activityRemain<money){
                //余额不足
                result.setResultCode(ActivityRemainResult.MONEY_UN_ENOUGH);
                result.setSuccess(false);
                return result;
            }
            result.setPreMoney(activityRemain);
            result.setChangeMoney(money);
            result.setAfterMoney(activityRemain-money);
            //开始操作redis库
            redisTemplate.opsForValue().set(key,String.valueOf(activityRemain-money));
        }catch (Exception e){
            //发生redis处理异常
            e.printStackTrace();
            result.setResultCode(ActivityRemainResult.REDIS_ERROR);
            result.setSuccess(false);
            return result;
        }
        return result;
    }
}
