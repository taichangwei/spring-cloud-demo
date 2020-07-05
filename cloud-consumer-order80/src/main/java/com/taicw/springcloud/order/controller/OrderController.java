package com.taicw.springcloud.order.controller;

import com.taicw.springcloud.entities.CommonResult;
import com.taicw.springcloud.entities.Payment;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author taichangwei
 */
@RestController
@RequestMapping(value = "/payment")
public class OrderController {

    public static final String PAYMENT_URL = "http://localhost:8001";
    public static final String CLOUD_PAYMENT_URL = "http://payment-service";

    private final RestTemplate restTemplate;

    private final DiscoveryClient discoveryClient;

    public OrderController(RestTemplate restTemplate, DiscoveryClient discoveryClient) {
        this.restTemplate = restTemplate;
        this.discoveryClient = discoveryClient;
    }

    @PostMapping
    public CommonResult<Payment> creatPayment(@RequestBody Payment payment) {
        return restTemplate.postForObject(CLOUD_PAYMENT_URL + "/payment", payment, CommonResult.class);
    }

    @GetMapping(value = "/{id}")
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


}
