package com.hmall.gateway.routers;

import cn.hutool.json.JSONUtil;
import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;

@Component
@Slf4j
@RequiredArgsConstructor
public class DynamicRouterLoader {

    /**
     * 注入NacosConfigManager
     */
    private final NacosConfigManager configManager;
    private final RouteDefinitionWriter routeDefinitionWriter;
    //用于存放路由id
    private final Set<String> routeIds = new HashSet<>();

    private final String dataId = "gateway-routes.json";

    private final String group = "DEFAULT_GROUP";
    /**
     * PostConstruct  Spring 完成依赖注入（IOC 容器初始化好 Bean）之后，自动调用这个方法
     */
    @PostConstruct
    public void initRouterConfigListener() throws NacosException {

        ConfigService configService = configManager.getConfigService();
        // 1.项目启动的时候先拉取一次配置,并添加一个监听器
        String configInfo = configService.getConfigAndSignListener(dataId, group, 500, new Listener() {
            //线程池
            @Override
            public Executor getExecutor() {
                return null;
            }

            // 当配置变更的时候需要实现的功能
            @Override
            public void receiveConfigInfo(String configInfo) {
                //2.监听到配置变更
                updateConfigInfo(configInfo);
            }
        });
        //3. 第一次读取到配置,也需要更新到路由表
        updateConfigInfo(configInfo);
    }

    /**
     * 删除旧的路由表,更新为新的路由表
     * @param configInfo 路由表
     */
    public void updateConfigInfo(String configInfo){
        //1.解析配置信息
        List<RouteDefinition> routerDefinitions = JSONUtil.toList(configInfo, RouteDefinition.class);
        //2.删除旧的路由表
        for (String routeId : routeIds) {
            routeDefinitionWriter.delete(Mono.just(routeId)).subscribe();
        }
        //清除route id
        routeIds.clear();
        //3.更新路由表
        for(RouteDefinition route : routerDefinitions){
            // 3.1 更新路由表
            routeDefinitionWriter.save(Mono.just(route)).subscribe();
            // 3.2 记录路由ID,便于下次删除
            routeIds.add(route.getId());
        }
    }
}
