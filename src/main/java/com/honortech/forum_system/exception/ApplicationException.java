package com.honortech.forum_system.exception;

import com.honortech.forum_system.common.AppResult;

/**
 * 自定义异常
 */
public class ApplicationException extends RuntimeException {
    protected AppResult errorResult;

    /**
     * 构造方法
     * @param errorResult
     */
    public ApplicationException(AppResult errorResult) {
        super(errorResult.getMessage());
        this.errorResult = errorResult;
    }

    public ApplicationException(String message) {
        super(message);
    }

    public ApplicationException(String message, AppResult errorResult) {
        super(message);
        this.errorResult = errorResult;
    }

    public ApplicationException(String message, Throwable cause, AppResult errorResult) {
        super(message, cause);
        this.errorResult = errorResult;
    }

    public ApplicationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, AppResult errorResult) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.errorResult = errorResult;
    }

    public ApplicationException(Throwable cause, AppResult errorResult) {
        super(cause);
        this.errorResult = errorResult;
    }

    public AppResult getErrorResult() {
        return errorResult;
    }
}
