package com.hmall.gateway.filters;

import lombok.Data;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class PrinterAny2GatewayFilterFactory extends AbstractGatewayFilterFactory<PrinterAny2GatewayFilterFactory.Config> {

    /**
     * OrderedGatewayFilter基于装饰模式,让我们可以设置filter的优先级
     * 不需要优先级就使用GatewayFilter
     * @param config
     * @return
     */
    @Override
    public GatewayFilter apply(Config config) {
        return new OrderedGatewayFilter(new GatewayFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                String a = config.getA();
                String b = config.getB();
                String c = config.getC();
                System.out.println("a=" + a);
                System.out.println("b=" + b);
                System.out.println("c=" + c);
                return chain.filter(exchange);
            }
        },1);
    }

    @Data
    public static class Config{
        private String a;
        private String b;
        private String c;
    }


    public PrinterAny2GatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return List.of("a", "b", "c");
    }
}
