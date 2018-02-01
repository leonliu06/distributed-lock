package net.mrliuli.aspect;

/**
 * Created by li.liu on 2018/2/1.
 */
public class NotGetLockException extends Exception {

    public NotGetLockException(){}

    public NotGetLockException(String msg){
        super(msg);
    }

}
