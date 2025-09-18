package com.hmall.pay;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.hmall.pay.mapper")
public class payApplicaton {
    public static void main(String[] args) {
        SpringApplication.run(payApplicaton.class,args);
    }
}