package com.taicw.springcloud.payment.controller;

import com.taicw.springcloud.entities.CommonResult;
import com.taicw.springcloud.entities.Payment;
import com.taicw.springcloud.payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * @author taichangwei
 */
@RestController
@RequestMapping(value = "/payment")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Value("${server.port}")
    private String serverPort;

    @PostMapping
    public CommonResult<Payment> create(@RequestBody Payment payment){
        int result = paymentService.create(payment);
        if (result > 0) {
            return new CommonResult<>(200, "server-port:" + serverPort + "插入数据成功", payment);
        } else {
            return new CommonResult<>(500, "server-port:" + serverPort + "插入数据失败");
        }
    }

    @GetMapping(value = "/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable(value = "id") Long id){
        Payment payment = paymentService.getPaymentById(id);
        if (payment != null) {
            return new CommonResult<>(200, "server-port:" + serverPort + "查询数据成功", payment);
        } else {
            return new CommonResult<>(404, "server-port:" + serverPort + "查询数据失败");
        }
    }

    @GetMapping("/lb")
    public String testLoadBalanced(){
        return "current payment serverPort: " + serverPort + " | " + UUID.randomUUID().toString();
    }

}
