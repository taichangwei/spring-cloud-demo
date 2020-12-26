package com.taicw.springcloud.order.controller;

import com.taicw.springcloud.entities.CommonResult;
import com.taicw.springcloud.entities.Payment;
import com.taicw.springcloud.order.service.PaymentFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author taichangwei
 */
@RestController
@RequestMapping(value = "/order")
public class OrderController {

    @Autowired
    private PaymentFeignService paymentFeignService;

    @PostMapping(value = "/payment")
    CommonResult<Payment> createPayment(@RequestBody Payment payment){
        return paymentFeignService.creatPayment(payment);
    }

    @GetMapping(value = "/payment/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable(value = "id") Long id){
        return paymentFeignService.getPaymentById(id);
    }

    /**
     * 测试 feign 调用服务超时。（默认超时时间是1秒，可以自行调整超时时间见配置文件）
     */
    @GetMapping(value = "/payment/timeOut/{time}")
    public String getPaymentById(@PathVariable(value = "time") Integer time){
        return paymentFeignService.testTimeOut(time);
    }

}













