package com.honortech.forum_system.common;

public enum ResultCode {
    SUCCESS                     (0, "operation success"),
    FAILED                      (1000, "operation failed"),
    FAILED_UNAUTHORIZED         (1001, "unauthorized failure"),
    FAILED_PARAMS_VALIDATE      (1002, "params validate failure"),
    FAILED_FORBIDDEN            (1003, "visit forbidden failure"),
    FAILED_CREATE               (1004, "create failure"),
    FAILED_NOT_EXISTS           (1005, "resource not exists"),
    FAILED_USER_EXISTS          (1101, "user already exists"),
    FAILED_USER_NOT_EXISTS      (1102, "user not exists"),
    FAILED_LOGIN                (1103, "login failure"),
    FAILED_USER_BANNED          (1104, "user banned"),
    FAILED_TWO_PWD_NOT_SAME     (1105, "password not match"),
    ERROR_SERVICES              (2000, "sever error"),
    ERROR_IS_NULL               (2001, "null error");



    int code;
    String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String toString() {
        return "ResultCode{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }
}
