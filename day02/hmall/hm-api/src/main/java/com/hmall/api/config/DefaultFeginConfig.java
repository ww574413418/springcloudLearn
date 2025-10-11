package com.hmall.api.config;


import com.hmall.api.client.fallback.itemClientFallBackFactory;
import com.hmall.common.utils.UserContext;
import feign.Logger;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;

import java.time.temporal.Temporal;

public class DefaultFeginConfig {
    @Bean
    public Logger.Level feignLogLevel(){
        return Logger.Level.FULL;
    }

    /**
     * 配置有个用户拦截器,
     * 每次发送请求的时候,都将用户信息放入请求的head头中
     * @return
     */
    @Bean
    public RequestInterceptor userInfoRequestInterrceptor(){
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate requestTemplate) {
                Long userId = UserContext.getUser();
                if (userId != null) {
                    requestTemplate.header("user-info", userId.toString());
                }
            }
        };
    }

    @Bean
    public itemClientFallBackFactory fallbackFactory(){
        return new itemClientFallBackFactory();
    }
}
