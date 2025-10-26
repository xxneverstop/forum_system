package com.honortech.forum_system.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * 配置 mybatis 扫描路径
 */
// 加入spring
@Configuration
// 配置扫描路径
@MapperScan("com.honortech.forum_system.dao")
public class MybatisConfig {
}
