package com.taicw.springcloud.paymenthyx.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author taichangwei
 */
@Service
public class PaymentService {

    public String paymentInfoOk(Integer id){
        return "线程池：" + Thread.currentThread().getName() + "  |  paymentInfoOk, id:" + id + " | (￣▽￣)~* 哈哈哈";
    }



    @HystrixCommand(
            fallbackMethod = "paymentInfoTimeFallback",
            commandProperties = {
                    //调用时间超过3秒钟，则走paymentInfoTimeFallback方法
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
            }
    )
    public String paymentInfoTimeOut(Integer id) {
        //id输入0时可以模拟异常的情况
        int a = 100/id;
        int time = id;
        try {TimeUnit.SECONDS.sleep(time);} catch (InterruptedException e) {e.printStackTrace();}
        return "线程池：" + Thread.currentThread().getName() + "  |  paymentInfoTimeOut, id:" + id + " | (；′⌒`) 呜呜，耗时：" + time;
    }

    /**
     * paymentInfoTimeOut 方法调用超时或异常的兜底方法
     */
    public String paymentInfoTimeFallback(Integer id){
        return "线程池：" + Thread.currentThread().getName() + "  |  paymentInfoTimeOut, id:" + id + " | 系统繁忙，请稍后再试";
    }

}
