package net.mrliuli.aspect;

import org.springframework.stereotype.Component;

/**
 * Created by li.liu on 2018/1/30.
 */
public class LockEntity {

    private String key;

    public LockEntity(){
    }

    public LockEntity(String key){
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


    private LockResult lockResult;

    public static class LockResult{

        private Boolean success;

        public LockResult(){

        }

        public LockResult(Boolean success){
            this.success = success;
        }

        public Boolean getSuccess() {
            return success;
        }

        public void setSuccess(Boolean success) {
            this.success = success;
        }
    }

    public LockResult getLockResult() {
        return lockResult;
    }

    public void setLockResult(LockResult lockResult) {
        this.lockResult = lockResult;
    }
}
