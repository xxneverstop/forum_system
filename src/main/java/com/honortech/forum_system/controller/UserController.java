package com.honortech.forum_system.controller;

import com.honortech.forum_system.common.AppResult;
import com.honortech.forum_system.common.ResultCode;
import com.honortech.forum_system.model.User;
import com.honortech.forum_system.services.IUserService;
import com.honortech.forum_system.utils.MD5Util;
import com.honortech.forum_system.utils.StringUtil;
import com.honortech.forum_system.utils.UUIDUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.annotation.Resource;
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
            @Parameter(description = "用户名") @RequestParam String username,
            @Parameter(description = "昵称") @RequestParam String nickname,
            @Parameter(description = "邮箱") @RequestParam String email,
            @Parameter(description = "密码") @RequestParam String password,
            @Parameter(description = "确认密码") @RequestParam String confirmPassword
            ) {
        if (StringUtil.isEmpty(username)
                ||  StringUtil.isEmpty(nickname)
                || StringUtil.isEmpty(email)
                || StringUtil.isEmpty(password)
                || StringUtil.isEmpty(confirmPassword)) {
            return AppResult.fail(ResultCode.FAILED_TWO_PWD_NOT_SAME);
        }

        User user = new User();
        user.setUsername(username);
        user.setNickname(nickname);
        user.setEmail(email);
        String salt = UUIDUtil.UUID_32();
        String encryptPassword = MD5Util.md5(password, salt);

        user.setSalt(salt);
        user.setPassword(encryptPassword);

        userService.createNormalUser(user);

        return null;
    }
}
