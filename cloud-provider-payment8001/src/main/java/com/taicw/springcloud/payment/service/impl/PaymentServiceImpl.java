package com.taicw.springcloud.payment.service.impl;

import com.taicw.springcloud.entities.Payment;
import com.taicw.springcloud.payment.dao.PaymentDao;
import com.taicw.springcloud.payment.service.PaymentService;
import org.springframework.stereotype.Service;

/**
 * @author taichangwei
 */
@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentDao paymentDao;

    public PaymentServiceImpl(PaymentDao paymentDao) {
        this.paymentDao = paymentDao;
    }

    @Override
    public int create(Payment payment) {
        return paymentDao.create(payment);
    }

    @Override
    public Payment getPaymentById(Long id) {
        return paymentDao.getPaymentById(id);
    }
}
