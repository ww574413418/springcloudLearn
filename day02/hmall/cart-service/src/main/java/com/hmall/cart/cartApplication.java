package com.hmall.cart;


import com.hmall.api.config.DefaultFeginConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@MapperScan("com.hmall.cart.mapper")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.hmall.api.client",defaultConfiguration = DefaultFeginConfig.class)
public class cartApplication {
    public static void main(String[] args) {
        SpringApplication.run(cartApplication.class,args);
    }

    /**
     * 配置RestTemplate
     * @return RestTemplate对象
     */
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}