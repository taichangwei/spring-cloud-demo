package com.taicw.springcloud.order;

import com.taicw.springcloud.ribbon.CustomRibbonRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

/**
 * @author taichangwei
 */
@SpringBootApplication
//@EnableEurekaClient 只针对eureka作为注册中心适用，@EnableDiscoveryClient 适用于所有其他注册中心，更为通用
@EnableDiscoveryClient
@RibbonClient(name = "payment-service", configuration = CustomRibbonRule.class)
public class OrderMain80 {

    public static void main(String[] args) {
        SpringApplication.run(OrderMain80.class);
    }
}
