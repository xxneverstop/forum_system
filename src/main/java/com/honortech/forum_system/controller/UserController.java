package com.honortech.forum_system.controller;

import com.honortech.forum_system.common.AppResult;
import com.honortech.forum_system.common.ResultCode;
import com.honortech.forum_system.config.AppConfig;
import com.honortech.forum_system.exception.ApplicationException;
import com.honortech.forum_system.model.User;
import com.honortech.forum_system.services.IUserService;
import com.honortech.forum_system.utils.MD5Util;
import com.honortech.forum_system.utils.StringUtil;
import com.honortech.forum_system.utils.UUIDUtil;
import com.sun.istack.NotNull;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Tag(name = "用户接口", description = "用于用户操作")
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private IUserService userService;
    
    @Operation( summary = "注册账号",
            description = "通过信息注册账号"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "注册成功"),
            @ApiResponse(responseCode = "400", description = "参数校验失败")
    })
    @PostMapping("/register")
    public AppResult register (
            @Parameter(description = "用户名") @RequestParam(value = "username") @NonNull String username,
            @Parameter(description = "昵称") @RequestParam(value = "nickname") @NonNull String nickname,

//            @Parameter(description = "邮箱") @RequestParam(value = "email") @NonNull String email,
            @Parameter(description = "邮箱") @RequestParam(value = "email", required = false) String email,   // 测试时不填入email

            @Parameter(description = "密码") @RequestParam(value = "password") @NonNull String password,
            @Parameter(description = "确认密码") @RequestParam(value = "passwordRepeat") @NonNull String passwordRepeat
            ) {
        User user = new User();
        user.setUsername(username);
        user.setNickname(nickname);

        if( ! password.equals(passwordRepeat)) {
            log.info(ResultCode.FAILED_TWO_PWD_NOT_SAME.toString());
//            throw new ApplicationException(AppResult.fail(ResultCode.FAILED_TWO_PWD_NOT_SAME));
            return AppResult.fail(ResultCode.FAILED_TWO_PWD_NOT_SAME);
        }

        user.setEmail(email);
        String salt = UUIDUtil.UUID_32();
        String encryptPassword = MD5Util.md5(password, salt);

        user.setSalt(salt);
        user.setPassword(encryptPassword);

        userService.createNormalUser(user);

        return AppResult.success("register success");
    }

    @Operation( summary = "登录账号",
            description = "通过账号用户名密码登录"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "登录成功"),
            @ApiResponse(responseCode = "400", description = "登录失败")
    })
    @PostMapping("/login")
    public AppResult login (
            HttpServletRequest request,
            @Parameter(description = "用户名") @RequestParam(value = "username") @NonNull String username,
            @Parameter(description = "密码") @RequestParam(value = "password") @NonNull String password
    ) {
        User user = userService.login(username, password);
        if (user == null) {
            log.info(ResultCode.FAILED_LOGIN.toString());
//            throw new ApplicationException(AppResult.fail(ResultCode.FAILED_LOGIN));
            return AppResult.fail(ResultCode.FAILED_LOGIN);
        }

        // 登录成功将 user 加入到 session 作用域
        // 获取当前请求的会话，如果当前请求没有会话，则 创建一个新的会话。
        HttpSession session = request.getSession(true);
        session.setAttribute(AppConfig.USER_SESSION, user);
        return AppResult.success("login success");
    }

    @Operation( summary = "获取用户信息",
            description = "通过id获取用户信息"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "获取成功"),
            @ApiResponse(responseCode = "400", description = "获取失败")
    })
    @GetMapping("info")
    public AppResult<User> getUserInfo(HttpServletRequest request,
                                       @Parameter(description = "用户id") @RequestParam(value = "id", required = false) Long id) {
        User user = null;
        if (id == null) {
            // id 为空，从session中获取当前登录的用户信息
            HttpSession session = request.getSession(false);
//            System.out.println("session=" + session);

            // 从session中获取
            user = (User) session.getAttribute(AppConfig.USER_SESSION);
        }
        else {
            user = userService.selectById(id);
        }

        if (user == null) {
            return AppResult.fail(ResultCode.FAILED_USER_NOT_EXISTS);
        }

        return AppResult.success(user);
    }

    @Operation( summary = "退出登录",
            description = "退出登录"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "退出成功"),
            @ApiResponse(responseCode = "400", description = "退出失败")
    })
    @GetMapping("logout")
    public AppResult logout (HttpServletRequest request) {
        // 获取当前请求的会话，如果当前请求没有会话，则 不创建新的会话，直接返回 null。
        HttpSession session = request.getSession(false);
        if (session != null) {
            log.info("session logout success");
            session.invalidate();
        }

        return AppResult.success("logout success");
    }
}
