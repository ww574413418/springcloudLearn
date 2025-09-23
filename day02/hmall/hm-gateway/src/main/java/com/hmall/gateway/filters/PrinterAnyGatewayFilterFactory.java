package com.hmall.gateway.filters;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class PrinterAnyGatewayFilterFactory extends AbstractGatewayFilterFactory {

    /**
     * OrderedGatewayFilter基于装饰模式,让我们可以设置filter的优先级
     * 不需要优先级就使用GatewayFilter
     * @param config
     * @return
     */
    @Override
    public GatewayFilter apply(Object config) {
        return new OrderedGatewayFilter(new GatewayFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                System.out.println("print any filter running");
                return chain.filter(exchange);
            }
        },1);
    }

}
