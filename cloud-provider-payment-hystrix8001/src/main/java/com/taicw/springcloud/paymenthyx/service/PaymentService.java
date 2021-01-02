package com.taicw.springcloud.paymenthyx.service;

import cn.hutool.core.util.IdUtil;
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
        return "线程池：" + Thread.currentThread().getName() + "  |  paymentInfoTimeFallback, id:" + id + " | 系统繁忙，请稍后再试";
    }


    // ===================================服务熔断=====================================================

    @HystrixCommand(fallbackMethod = "paymentCircuitBreakerFallback",
            commandProperties = {
                    @HystrixProperty(name = "circuitBreaker.enabled",value = "true"),// 开启断路器
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "10000"), // 时间窗口期
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"),// 请求次数
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "60") // 失败率达到多少后跳闸
            }
    )
    public String paymentCircuitBreaker(Integer id) {
        if (id < 0){
            throw new RuntimeException("******id 不能负数");
        }
        return "线程池：" + Thread.currentThread().getName() + "  | 调用成功，流水号: " + IdUtil.simpleUUID();
    }
    public String paymentCircuitBreakerFallback(Integer id){
        return "id 不能负数，请稍后再试，/(ㄒoㄒ)/~~   id: " + id;
    }

}
