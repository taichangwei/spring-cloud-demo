package com.taicw.springcloud.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author taichangwei
 */

@Configuration
public class GateWayConfig {

    /**
     * Gateway网关路由配置有两种方式：
     * 第一种：配置文件中配置（见application.yml）
     * 第二种：使用javaBean配置
     * 通过javaBean配置的路由优先级高于配置文件
     */
    @Bean
    RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        RouteLocatorBuilder.Builder routes = builder.routes();
        routes.route("baidu-route",
                p -> p.path("/**")
                        .and()
                        .query("goto", "baidu")
                        .uri("http://news.baidu.com")
        );
        return routes.build();
    }

}
