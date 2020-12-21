package com.taicw.springcloud.order.lb;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

/**
 * @author taichangwei
 */
public interface CustomLoadBalancer {

    /**
     * 通过自定义负载均衡算法获取服务实例
     * @param instances 服务实例列表
     * @return 返回选中的服务实例
     */
    ServiceInstance getInstances(List<ServiceInstance> instances);
}
