package com.zk.vo;

public class ActivityRemainResult {

    public static String MONEY_UN_ENOUGH="F_AC_0001";//余额不足
    public static String LOCK_FAIL="F_AC_0002";//获取锁失败
    public static String REDIS_ERROR="F_AC_0003";//redis服务异常
    public static String SUCCESS="F_AC_0004";//成功

    private String resultCode;
    private boolean success;
    private long preMoney;
    private long changeMoney;
    private long afterMoney;

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public long getPreMoney() {
        return preMoney;
    }

    public void setPreMoney(long preMoney) {
        this.preMoney = preMoney;
    }

    public long getChangeMoney() {
        return changeMoney;
    }

    public void setChangeMoney(long changeMoney) {
        this.changeMoney = changeMoney;
    }

    public long getAfterMoney() {
        return afterMoney;
    }

    public void setAfterMoney(long afterMoney) {
        this.afterMoney = afterMoney;
    }
}
