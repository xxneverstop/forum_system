package com.honortech.forum_system.exception;


import com.honortech.forum_system.common.AppResult;
import com.honortech.forum_system.common.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理类
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理自定义的已知异常
     * @param e
     * @return
     */
    // 以body形式返回
    @ResponseBody
    // （）中是指定要处理哪种异常
    @ExceptionHandler(ApplicationException.class)
    public AppResult applicationException(ApplicationException e) {
        e.printStackTrace(); // 异常信息，上生产时删除

        //
        log.error(e.getMessage());
        if(e.getErrorResult() != null) {
            return e.getErrorResult();
        }

        // 非空校验
        if(e.getMessage() == null || e.getMessage().isEmpty()) {
            return AppResult.fail(ResultCode.ERROR_SERVICES);
        }

        // 具体异常信息
        return AppResult.fail(e.getMessage());
    }

    /**
     * 处理未捕获的其他异常
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public AppResult exceptionHandler(Exception e) {
        // 打印异常
        e.printStackTrace();

        log.error(e.getMessage());

        if(e.getMessage() == null) {
            return AppResult.fail(ResultCode.ERROR_SERVICES);
        }
        return AppResult.fail(e.getMessage());
    }
}
