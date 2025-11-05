package com.honortech.forum_system.common;

public class AppResult<T> {
    // 状态码
    private long code;
    private String message;
    private T data;

    public AppResult() {}
    public AppResult(long code, String message) {
        this(code, message, null);
    }
    public AppResult(long code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }


    public static AppResult success() {
        return new AppResult(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage());
    }
    public static AppResult success(String message) {
        return new AppResult(ResultCode.SUCCESS.getCode(), message);
    }
    public static <T> AppResult<T> success(T data) {
        return new AppResult(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
    }

    public static <T> AppResult<T> success(String message, T data) {
        return new AppResult(ResultCode.SUCCESS.getCode(), message, data);
    }


    public static AppResult fail() {
        return new AppResult(ResultCode.FAILED.getCode(), ResultCode.FAILED.getMessage());
    }
    public static <T> AppResult<T> fail(String message) {
        return new AppResult(ResultCode.FAILED.getCode(), message);
    }
    public static <T> AppResult<T> fail(String message, T data) {
        return new AppResult(ResultCode.FAILED.getCode(), message, data);
    }
    public static <T> AppResult<T> fail(ResultCode resultCode) {
        return new AppResult(resultCode.getCode(), resultCode.getMessage());
    }

    public void setCode(long code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(T data) {
        this.data = data;
    }

    public long getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
