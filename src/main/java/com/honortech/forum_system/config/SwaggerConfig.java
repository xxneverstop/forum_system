package com.honortech.forum_system.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Swagger / OpenAPI 3 配置类
 * 适用于 Spring Boot 3.5.6 + Springdoc OpenAPI 2.x
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI forumSystemOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Forum System 接口文档")
                        .description("基于 Spring Boot 3.5.6 构建的论坛系统 API 文档")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("xxneverstop")
                                .url("https://github.com/xxneverstop")
                                .email("18000805038@163.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://www.apache.org/licenses/LICENSE-2.0.html"))
                )
                .servers(List.of(
                        new Server().url("http://localhost:27070").description("本地环境"),
                        new Server().url("https://api.forum.honortech.com").description("生产环境")
                ));
    }
}
