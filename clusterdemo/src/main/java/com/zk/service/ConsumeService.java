package com.zk.service;

import com.zk.constant.RedConstant;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public class ConsumeService {

    public void send(){
        //创建50个线程同时派发红包
        ExecutorService executor = Executors.newFixedThreadPool(1);
        List<Future<Boolean>> futures=new ArrayList<Future<Boolean>>();

        int successCount=0;
        int failCount=0;
        //50个线程同时去跑
        for(int i=0;i<70;i++){
            Future<Boolean> item=executor.submit(new SendWorker(i, RedConstant.ACTIVITY_NAME,RedConstant.SINGLE_MONEY));
            futures.add(item);
        }
        for(int i=0;i<futures.size();i++){
            try {
                boolean result=futures.get(i).get();
                if(result){
                    successCount++;
                }else {
                    failCount++;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            System.out.println(String.format("线程: %s 已经获取到结果!",i));
        }
        System.out.println(String.format("成功数量:%s",successCount));
        System.out.println(String.format("失败数量:%s",failCount));
    }
}
