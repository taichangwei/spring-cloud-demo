package com.taicw.springcloud.order.controller;

import com.taicw.springcloud.entities.CommonResult;
import com.taicw.springcloud.entities.Payment;
import com.taicw.springcloud.order.lb.CustomLoadBalancer;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * @author taichangwei
 */
@RestController
@RequestMapping(value = "/order")
public class OrderController {

    public static final String PAYMENT_URL = "http://localhost:8001";
    public static final String CLOUD_PAYMENT_URL = "http://payment-service";

    private final RestTemplate restTemplate;

    private final DiscoveryClient discoveryClient;

    private final CustomLoadBalancer customLoadBalancer;

    public OrderController(RestTemplate restTemplate, DiscoveryClient discoveryClient, CustomLoadBalancer customLoadBalancer) {
        this.restTemplate = restTemplate;
        this.discoveryClient = discoveryClient;
        this.customLoadBalancer = customLoadBalancer;
    }

    @PostMapping(value = "/payment")
    public CommonResult<Payment> creatPayment(@RequestBody Payment payment) {
        return restTemplate.postForObject(CLOUD_PAYMENT_URL + "/payment", payment, CommonResult.class);
    }

    @GetMapping(value = "/payment/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable(value = "id") Long id){
        return restTemplate.getForObject(CLOUD_PAYMENT_URL + "/payment/" + id, CommonResult.class);
    }

    @GetMapping("/discovery")
    public Object discovery(){
        Map<String, Object> result = new HashMap<>();
        List<String> services = discoveryClient.getServices();
        result.put("services", services);
        services.forEach(s -> {
            List<ServiceInstance> instances = discoveryClient.getInstances(s);
            result.put(s, instances);
            instances.forEach(i -> System.out.println("service:" + s + ", ip:" + i.getHost() +", port:"+i.getPort()));
        });
        result.put("discovery", discoveryClient);
        return result;
    }


    @GetMapping(value = "/payment/lb")
    public String testLoadBalanced() {
        return restTemplate.getForObject(CLOUD_PAYMENT_URL + "/payment/lb", String.class);
    }

    @GetMapping(value = "/payment/myLb")
    public String testMyLb() {
        // 这里的restTemplate是新创建的，并不是容器中的bean，因此没有负载均衡功能，需要手动选出具体的服务实例并获取该实例的url
        RestTemplate restTemplate = new RestTemplate();
        List<ServiceInstance> instances = discoveryClient.getInstances("payment-service");
        if (null == instances || 0 == instances.size()) {
            return "无可用payment-service服务";
        }
        ServiceInstance instance = customLoadBalancer.getInstances(instances);
        String url = instance.getUri().toString();
        return restTemplate.getForObject(url + "/payment/lb", String.class);
    }



}
