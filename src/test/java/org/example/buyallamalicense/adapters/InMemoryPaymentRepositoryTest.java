package org.example.buyallamalicense.adapters;

import org.example.buyallamalicense.app.model.ExternalPaymentId;
import org.example.buyallamalicense.app.model.Payment;
import org.example.buyallamalicense.app.model.PaymentId;
import org.example.buyallamalicense.app.ports.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

class InMemoryPaymentRepositoryTest {

    private PaymentRepository paymentRepository;

    @BeforeEach
    void setUp() {
        paymentRepository = new InMemoryPaymentRepository();
    }

    @Test
    void should_save_and_find_a_payment() {
        PaymentId paymentId = new PaymentId(42);
        var payment = new Payment(paymentId, new ExternalPaymentId());

        var id = paymentRepository.save(payment);

        then(paymentRepository.findById(id))
                .isEqualTo(payment);
    }
}