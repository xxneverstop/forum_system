package com.honortech.forum_system.controller;

import com.honortech.forum_system.common.AppResult;
import com.honortech.forum_system.common.ResultCode;
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
import jakarta.annotation.Resource;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
            @Parameter(description = "邮箱") @RequestParam(value = "email") @NonNull String email,
            @Parameter(description = "密码") @RequestParam(value = "password") @NonNull String password,
            @Parameter(description = "确认密码") @RequestParam(value = "confirmPassword") @NonNull String confirmPassword
            ) {
        User user = new User();
        user.setUsername(username);
        user.setNickname(nickname);

        if( ! password.equals(confirmPassword)) {
            log.info(ResultCode.FAILED_TWO_PWD_NOT_SAME.toString());
            throw new ApplicationException(AppResult.fail(ResultCode.FAILED_TWO_PWD_NOT_SAME));
        }

        user.setEmail(email);
        String salt = UUIDUtil.UUID_32();
        String encryptPassword = MD5Util.md5(password, salt);

        user.setSalt(salt);
        user.setPassword(encryptPassword);

        userService.createNormalUser(user);

        return AppResult.success("register success");
    }
}
