package com.taicw.springcloud.ribbon;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import com.netflix.loadbalancer.RoundRobinRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 修改ribbon的负载均衡策略为【随机】。
 * 官网明确给出警告：这个自定义配置类不能放在 @ComponentScan 所扫描的当前包以及子包下，否则我们自定的这个配置类就会被所有的Ribbon客户端所共享，达不到特殊定制化的目的
 *
 * 修改负载均衡的第二种方式：通过配置文件属性修改，见本模块 application.yml
 * 格式：serviceId.ribbon.NFLoadBalancerRuleClassName=com.netflix.loadbalancer.WeightedResponseTimeRule
 *
 * 属性配置 > JAVA_BEAN配置 > Netflix Ribbon默认配置。（通过实验，好像是 JAVA_BEAN配置 > 属性配置，并不像官方文档说的那样）
 * @author taichangwei
 */

@Configuration
public class CustomRibbonRule {

    @Bean
    public IRule customRule(){
//        return new RoundRobinRule();
        return new RandomRule();
    }
}
