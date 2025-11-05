package com.honortech.forum_system.services.impl;

import com.honortech.forum_system.model.User;
import com.honortech.forum_system.services.IUserService;
import com.honortech.forum_system.utils.MD5Util;
import com.honortech.forum_system.utils.UUIDUtil;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserServiceImplTest {
    @Resource
    private IUserService userService;

    @Test
    void createNormalUser() {
        User user =  new User();
        user.setUsername("honortech");
        user.setNickname("honortech");

        String password = "123456";
        String salt = UUIDUtil.UUID_32();
        user.setSalt(salt);
        user.setPassword(MD5Util.md5(password, salt));

        userService.createNormalUser(user);
        System.out.println(user);
    }
}