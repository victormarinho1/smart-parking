package com.fatec.smart_parking.payment;

import com.fatec.smart_parking.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public List<Payment> findAllByUser(Long userId) {
       return this.paymentRepository.findPaymentsByUserId(userId);

    }
}
