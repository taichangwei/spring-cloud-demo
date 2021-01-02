package com.taicw.springcloud.orderhyx.controller;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.taicw.springcloud.orderhyx.service.PaymentHystrixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author taichangwei
 */

@RestController
@RequestMapping(value = "/order")
@DefaultProperties(defaultFallback = "globalFallbackMethod")
public class OrderHystrixController {

    @Autowired
    private PaymentHystrixService paymentHystrixService;

    @Value("${server.port}")
    private Integer serverPort;

    @GetMapping("/payment/hystrix/ok/{id}")
    @HystrixCommand
    public String paymentInfoOk(@PathVariable("id") Integer id) {
        //当id输入为0时，模拟异常的情况
        int a = 100/id;
        String result = paymentHystrixService.paymentInfoOk(id);
        return "order: " + result;
    }

    @GetMapping("/payment/hystrix/timeout/{id}")
    @HystrixCommand(fallbackMethod = "paymentInfoTimeOutFallback",
            commandProperties = {
                    //调用时间超过3秒钟，则走paymentInfoTimeFallback方法
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
            }
    )
    public String paymentInfoTimeOut(@PathVariable("id") Integer id) {
        String result = paymentHystrixService.paymentInfoTimeOut(id);
        return "order: " + result;
    }

    /**
     * 针对于paymentInfoTimeOut方法的fallback
     */
    public String paymentInfoTimeOutFallback(Integer id){
        return "我是orderService:"+serverPort + ", 对方支付系统繁忙请10秒钟后再试或者自己运行出错请检查自己,o(╥﹏╥)o";
    }

    /**
     * 全局的FallbackMethod，由@DefaultProperties注解指明
     */
    public String globalFallbackMethod()
    {
        return "我是全局fallback方法，没有特别指明fallbackMethod就调用我。系统繁忙，请稍后再试，/(ㄒoㄒ)/~~";
    }

    /**
     * 测试针对feignClient配置的fallback。
     * 1.首先方法并没有添加 @HystrixCommand 注解；2.paymentService故意停掉模拟宕机的情况，触发feignClient走fallback
     */
    @GetMapping("/payment/hystrix/feign/{id}")
    public String testFeignFallback(@PathVariable("id") Integer id) {
        String result = paymentHystrixService.paymentInfoOk(id);
        return "order: " + result;
    }

}
