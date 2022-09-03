package com.asuna.store.service.ex;

/**
 * 业务层异常的基类：
 */
public class ServiceException extends RuntimeException{

    public ServiceException() {
        super();
    }

    //返回异常信息(常用)
    public ServiceException(String message) {
        super(message);
    }

    //返回异常信息和异常对象(常用)
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    protected ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
