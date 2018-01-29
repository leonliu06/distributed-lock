package net.mrliuli.config;

/**
 * Created by li.liu on 2018/1/29.
 */


public class LockConfigs {

    /**
     * 锁的过期时间，为0表示不设置过期时间
     */
    private int lockExpireMillis = 0;

    /**
     * 等待获取锁的时间，为0表示不等待
     */
    private int lockWaitingMillis = 3000;

    public LockConfigs(){
    }

    public LockConfigs(int lockExpireMillis, int lockWaitingMillis){
        this.lockExpireMillis = lockExpireMillis;
        this.lockWaitingMillis = lockWaitingMillis;
    }

    public int getLockExpireMillis() {
        return lockExpireMillis;
    }

    public void setLockExpireMillis(int lockExpireMillis) {
        this.lockExpireMillis = lockExpireMillis;
    }

    public int getLockWaitingMillis() {
        return lockWaitingMillis;
    }

    public void setLockWaitingMillis(int lockWaitingMillis) {
        this.lockWaitingMillis = lockWaitingMillis;
    }
}
