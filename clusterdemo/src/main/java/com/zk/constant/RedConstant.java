package com.zk.constant;

public class RedConstant {

    public static String LOCK_KEY="%s_LOCK";//每一个活动的锁
    public static String ACTIVITY_REMAIN="%s_REMAIN";//每一个活动的余额信息
    //设置红包的失效时间为200ms
    public static long EXPIRED=2000L;//超时时间
    //设置活动的初始额度为100000单位：分
    public static long MONEY=10000;//活动总额度
    public static long SINGLE_MONEY=200L;//单个红包金额

    public static String ACTIVITY_NAME="testActivity";//活动名称
}
