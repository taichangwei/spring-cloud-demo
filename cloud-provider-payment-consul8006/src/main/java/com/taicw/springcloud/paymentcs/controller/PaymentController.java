package com.taicw.springcloud.paymentcs.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author taichangwei
 */

@RestController
@RequestMapping(value = "/payment")
public class PaymentController {

    @Value("${server.port}")
    private String serverPort;

    @GetMapping(value = "/cs")
    public String paymentZk() {
        return "springCloud with consul，serverPort: " + serverPort + " " + UUID.randomUUID().toString();
    }

}
