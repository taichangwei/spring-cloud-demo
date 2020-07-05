package com.taicw.springcloud.payment.controller;

import com.taicw.springcloud.entities.CommonResult;
import com.taicw.springcloud.entities.Payment;
import com.taicw.springcloud.payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

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
            return new CommonResult<>(200, "插入数据成功，server-port:" + serverPort, payment);
        } else {
            return new CommonResult<>(500, "数据插入失败, server-port:" + serverPort);
        }
    }

    @GetMapping(value = "/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable(value = "id") Long id){
        Payment payment = paymentService.getPaymentById(id);
        if (payment != null) {
            return new CommonResult<>(200, "查询数据成功，server-port:" + serverPort, payment);
        } else {
            return new CommonResult<>(404, "查询数据失败，server-port:" + serverPort);
        }
    }

}
