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

}
