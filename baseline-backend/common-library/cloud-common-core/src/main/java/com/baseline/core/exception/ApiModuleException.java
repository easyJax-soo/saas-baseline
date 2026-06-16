package com.baseline.core.exception;

/**
 * API访问异常
 */
public class ApiModuleException extends RuntimeException {


    public ApiModuleException() {
        super();
    }


    public ApiModuleException(String message) {
        super(message);
    }


    public ApiModuleException(Throwable cause) {
        super(cause);
    }


    public ApiModuleException(String message, Throwable cause) {
        super(message, cause);
    }


}
