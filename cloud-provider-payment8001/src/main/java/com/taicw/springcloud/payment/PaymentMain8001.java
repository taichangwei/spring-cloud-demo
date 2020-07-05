package com.taicw.springcloud.payment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author taichangwei
 */
@SpringBootApplication
//@EnableEurekaClient 只针对eureka作为注册中心适用，@EnableDiscoveryClient 适用于所有其他注册中心，更为通用
@EnableDiscoveryClient
public class PaymentMain8001 {
    public static void main(String[] args) {
        SpringApplication.run(PaymentMain8001.class, args);
    }
}
