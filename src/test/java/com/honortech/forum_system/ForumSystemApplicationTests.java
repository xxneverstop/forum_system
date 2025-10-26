package com.honortech.forum_system;

import com.honortech.forum_system.dao.UserMapper;
import com.honortech.forum_system.model.User;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@SpringBootTest
class ForumSystemApplicationTests {

    // 数据源
    @Resource
    private DataSource dataSource;

    @Resource
    private UserMapper userMapper;

    @Test
    void testConnection() throws SQLException {
        System.out.println("dataSource = " + dataSource.getConnection());

        // 获取数据库连接
        Connection connection = dataSource.getConnection();
        System.out.println("connection = " + connection);
        connection.close();
    }

    @Test
    void testMybatis() {
        User user = userMapper.selectByPrimaryKey(1L);
        System.out.println("user = " + user);
        System.out.println(user.toString());
        System.out.println(user.getUsername());

    }
    @Test
    void contextLoads() {
        System.out.println("test code: Spring Boot, forum system");
    }

}
