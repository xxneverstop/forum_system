package com.honortech.forum_system.controller;

import com.honortech.forum_system.common.AppResult;
import com.honortech.forum_system.exception.ApplicationException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "测试接口", description = "用于测试接口与全局异常处理")
@RestController
@RequestMapping("/test")
public class TestController {

    @Operation( summary = "测试基本接口",
                description = "返回一个简单的字符串，用于测试 Spring Boot 是否启动成功"
    )
    @ApiResponse(responseCode = "200", description = "接口访问成功")
    @GetMapping("/hello")
    public String hello()
    {
        return "HELLO! HELLO! spring booot, i'm xrw";
    }

    @Operation(
            summary = "测试系统异常处理",
            description = "主动抛出一个系统级异常（Exception），用于验证全局异常捕获是否生效"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "500", description = "系统异常被 GlobalExceptionHandler 捕获")
    })
    @GetMapping("/exception")
    public AppResult testException() throws Exception {
        throw new Exception("this is an Exception");
    }

    @Operation(
            summary = "测试自定义异常处理",
            description = "主动抛出一个自定义异常（ApplicationException），用于验证自定义异常捕获逻辑"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "500", description = "自定义异常被 GlobalExceptionHandler 捕获并返回 AppResult")
    })
    @GetMapping("/appException")
    public AppResult testApplicationException() throws ApplicationException {
        throw new ApplicationException("this is an diy ApplicationException");
    }
}
