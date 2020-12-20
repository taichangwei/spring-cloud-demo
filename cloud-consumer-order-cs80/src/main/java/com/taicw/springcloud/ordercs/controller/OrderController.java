package com.taicw.springcloud.ordercs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author taichangwei
 */

@RestController
@RequestMapping(value = "/order")
public class OrderController {

    public static final String PAYMENT_URL = "http://localhost:8006";
    public static final String CLOUD_PAYMENT_URL = "http://paymentcs-serivce";


    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping(value = "/payment/cs")
    public String payment(){
        return restTemplate.getForObject(CLOUD_PAYMENT_URL + "/payment/cs", String.class);
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
}

