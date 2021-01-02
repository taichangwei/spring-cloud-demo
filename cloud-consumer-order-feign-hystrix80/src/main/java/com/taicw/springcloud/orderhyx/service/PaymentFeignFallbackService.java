package com.taicw.springcloud.orderhyx.service;

import com.taicw.springcloud.entities.CommonResult;
import com.taicw.springcloud.entities.Payment;
import org.springframework.stereotype.Component;

/**
 * @author taichangwei
 */
@Component
public class PaymentFeignFallbackService implements PaymentHystrixService {

    @Override
    public String paymentInfoOk(Integer id) {
        return "feignClient fallback for paymentInfoOk | id = " + id + "|系统异常，稍后再试";
    }

    @Override
    public String paymentInfoTimeOut(Integer id) {
        return "feignClient fallback for paymentInfoTimeOut| id ="  + id + "|系统异常，稍后再试";
    }
}
