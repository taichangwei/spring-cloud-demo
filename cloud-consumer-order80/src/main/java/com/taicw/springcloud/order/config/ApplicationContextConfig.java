package com.taicw.springcloud.order.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author taichangwei
 */
@Configuration
public class ApplicationContextConfig {

    @Bean
    public RestTemplate getTestTemplate(){
        return new RestTemplate();
    }
}
