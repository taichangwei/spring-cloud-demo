package com.taicw.springcloud.order.lb;


import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 简单模拟实现 RoundRobinRule 轮询算法，实现负载均衡
 * @author taichangwei
 */

@Component
public class MyRoundLB implements CustomLoadBalancer {

    private AtomicInteger nextServerCyclicCounter = new AtomicInteger(0);

    @Override
    public ServiceInstance getInstances(List<ServiceInstance> instances) {
        int serviceIndex = getNextIndex(instances.size());
        return instances.get(serviceIndex);
    }

    private int getNextIndex(int modulo) {
        int current;
        int next;
        do {
            current = nextServerCyclicCounter.get();
            next = current >= Integer.MAX_VALUE ? 0 : current + 1;
        } while (!nextServerCyclicCounter.compareAndSet(current, next));
        System.out.println("*******第" + next + "次访问******");
        return next % modulo;
    }
}















