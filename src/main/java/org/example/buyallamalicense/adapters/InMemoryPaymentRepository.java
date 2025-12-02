package org.example.buyallamalicense.adapters;

import org.example.buyallamalicense.app.model.Payment;
import org.example.buyallamalicense.app.model.PaymentId;
import org.example.buyallamalicense.app.ports.PaymentRepository;

public class InMemoryPaymentRepository implements PaymentRepository {
    @Override
    public PaymentId save(Payment payment) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
