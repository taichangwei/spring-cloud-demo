package com.taicw.springcloud.order.controller;

import com.taicw.springcloud.entities.CommonResult;
import com.taicw.springcloud.entities.Payment;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

/**
 * @author taichangwei
 */
@RestController
@RequestMapping(value = "/payment")
public class OrderController {

    public static final String PAYMENT_URL = "http://localhost:8001";

    private final RestTemplate restTemplate;


    public OrderController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping
    public CommonResult<Payment> creatPayment(@RequestBody Payment payment) {
        return restTemplate.postForObject(PAYMENT_URL + "/payment", payment, CommonResult.class);
    }

    @GetMapping(value = "/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable(value = "id") Long id){
        return restTemplate.getForObject(PAYMENT_URL + "/payment/" + id, CommonResult.class);
    }


}
