package com.hmall.gateway.filters;

import com.hmall.gateway.config.AuthProperties;
import com.hmall.gateway.utils.JwtTool;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.RequestPath;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * 进行登陆校验
 */
@Component
@RequiredArgsConstructor
public class AuthGlobeFilter implements GlobalFilter, Ordered {

    private final AuthProperties authProperties;

    private final JwtTool jwtTool;

    private final AntPathMatcher antPathMatcher;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 1.获取request对象
        ServerHttpRequest request = exchange.getRequest();
        // 2. 判断是否需要登陆拦截
        RequestPath path = request.getPath();
        List<String> excludePaths = authProperties.getExcludePaths();
        // 2.1 判断请求连接是否需要拦截
        if(isExcluede(path.toString(),excludePaths)){
            return chain.filter(exchange);
        }
        // 3. 获取token
        List<String> tokens = request.getHeaders().get("authorization");
        String token = null;
        // 4.校验token
        if (tokens != null && !tokens.isEmpty()){
            token = tokens.get(0);
        }
        Long userId = null;
        try {
            userId = jwtTool.parseToken(token);
        } catch (Exception e) {
            ServerHttpResponse response = exchange.getResponse();
            //将回复的请求头定义为未授权
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }
        // 5. 传递用户信息
        String userInfo = userId.toString();
        ServerWebExchange swe = exchange.mutate()
                .request(builder -> builder.header("user-info", userInfo))
                .build();
        // 放行
        return chain.filter(swe);
    }

    // 判断路径
    private boolean isExcluede(String path, List<String> excludePaths) {
        for(String pathPattern:excludePaths){
            // 判断路径是否匹配 含通配符
            if ( antPathMatcher.match(pathPattern,path)) {
               return true;
            }
        }
        return false;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
