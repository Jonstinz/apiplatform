package com.apiplatform.apiplatform_backend;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.apiplatform.apiplatform_backend.mapper")
@EnableDubbo
public class ApiPlatformBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiPlatformBackendApplication.class, args);

    }

}
