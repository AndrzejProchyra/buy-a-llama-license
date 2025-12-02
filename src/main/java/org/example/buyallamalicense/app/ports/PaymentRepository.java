package org.example.buyallamalicense.app.ports;

import org.example.buyallamalicense.app.model.Payment;
import org.example.buyallamalicense.app.model.PaymentId;

public interface PaymentRepository {
    PaymentId save(Payment payment);

    Payment findById(PaymentId id);
}
