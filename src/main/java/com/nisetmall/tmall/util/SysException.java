package com.nisetmall.tmall.util;

//跳转到友好的异常错误页面

public class SysException extends Exception {
    private String message;
    public SysException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}

