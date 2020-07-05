package com.taicw.springcloud.payment.service;


import com.taicw.springcloud.entities.Payment;

/**
 * @author taichangwei
 */
public interface PaymentService {

    public int create(Payment payment);

    public Payment getPaymentById(Long id);
}
