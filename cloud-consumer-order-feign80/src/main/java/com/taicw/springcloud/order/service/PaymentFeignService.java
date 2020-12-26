package com.taicw.springcloud.order.service;

import com.taicw.springcloud.entities.CommonResult;
import com.taicw.springcloud.entities.Payment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author taichangwei
 */

@Component
@FeignClient(value = "payment-service")
public interface PaymentFeignService {

    @PostMapping(value = "/payment")
    CommonResult<Payment> creatPayment(@RequestBody Payment payment);

    @GetMapping(value = "/payment/{id}")
    CommonResult<Payment> getPaymentById(@PathVariable(value = "id") Long id);

    @GetMapping("/payment/timeOut/{time}")
    String testTimeOut(@PathVariable(value = "time") Integer time);

}
