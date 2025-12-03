package org.example.buyallamalicense.app.ports.repository;

import org.example.buyallamalicense.app.model.Payment;
import org.example.buyallamalicense.app.model.PaymentId;

import java.util.Optional;

public interface PaymentRepository {
    PaymentId save(Payment payment);

    Optional<Payment> findById(PaymentId paymentId);
}
