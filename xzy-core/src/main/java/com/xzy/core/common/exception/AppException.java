package com.xzy.core.common.exception;

/**
 * @author xzy
 * 统一 APP Exception
 */
public class AppException extends Exception {
    private String msg;
    private String code = "500";

    public AppException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public AppException(Throwable cause, String msg) {
        super(msg, cause);
        this.msg = msg;
    }

    public AppException(String msg, String code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public AppException(Throwable cause, String msg, String code) {
        super(msg, cause);
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "AppException{" + "msg='" + msg + '\'' + ", code='" + code + '\'' + '}';
    }

    public String toJson() {
        return "{" + "msg='" + msg + '\'' + ", code='" + code + '\'' + '}';
    }
}
