package com.hmall.trade;


import com.hmall.api.config.DefaultFeginConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@MapperScan("com.hmall.trade.mapper")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.hmall.api.client",defaultConfiguration = DefaultFeginConfig.class)
public class tradeApplication {
    public static void main(String[] args) {

        SpringApplication.run(tradeApplication.class,args);

    }
}