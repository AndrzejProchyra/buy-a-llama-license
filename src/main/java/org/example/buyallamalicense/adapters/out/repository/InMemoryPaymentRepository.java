package org.example.buyallamalicense.adapters.out.repository;

import org.example.buyallamalicense.app.model.Payment;
import org.example.buyallamalicense.app.model.PaymentId;
import org.example.buyallamalicense.app.ports.PaymentRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryPaymentRepository implements PaymentRepository {

    private final Map<PaymentId, Payment> payments = new HashMap<>();
    private final AtomicLong idSequence = new AtomicLong();

    @Override
    public PaymentId save(Payment payment) {
        var paymentId = payment.id();
        if (paymentId == null) {
            paymentId = new PaymentId(idSequence.incrementAndGet());
            payment = payment.withId(paymentId);
        }
        payments.put(paymentId, payment);
        return paymentId;
    }

    @Override
    public Optional<Payment> findById(PaymentId paymentId) {
        return Optional.ofNullable(payments.get(paymentId));
    }
}
