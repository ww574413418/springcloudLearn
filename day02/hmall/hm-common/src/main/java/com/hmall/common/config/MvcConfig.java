package com.hmall.common.config;

import com.hmall.common.interrceptors.userInfoInterrecptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
/**条件注册,只要有spring MVC 这个配置才生效
 * 因为getaway中没有使用MVC,使用的是flux,这样gateway在启动时会报错
 */
@ConditionalOnClass(DispatcherServlet.class)
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new userInfoInterrecptor());
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
