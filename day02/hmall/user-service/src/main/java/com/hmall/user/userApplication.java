package com.hmall.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.hmall.user.mapper")
public class userApplication {
    public static void main(String[] args) {
        SpringApplication.run(userApplication.class,args);
    }
}