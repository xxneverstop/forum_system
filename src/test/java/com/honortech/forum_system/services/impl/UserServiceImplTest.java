package com.honortech.forum_system.services.impl;

import com.honortech.forum_system.model.User;
import com.honortech.forum_system.services.IUserService;
import com.honortech.forum_system.utils.MD5Util;
import com.honortech.forum_system.utils.UUIDUtil;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class UserServiceImplTest {
    @Resource
    private IUserService userService;

    @Transactional
    // 加上 @Transactional
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

    @Test
    void selectByUsername() {
        User user = userService.selectByUsername("testUser1");
        System.out.println(user);
    }

    @Test
    void login() {
        User user = userService.login("testUser1", "111111");
        System.out.println(user);
    }

    @Test
    void selectById() {
        User user = userService.selectById(4L);
        System.out.println(user);
    }

    @Test
    @Transactional
    void addOneArticleCountById() {
        userService.addOneArticleCountById(1L);
        System.out.println("update success");
    }
}