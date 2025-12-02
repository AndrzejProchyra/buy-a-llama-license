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
        var paymentId = new PaymentId(42);
        var payment = new Payment(paymentId, new ExternalPaymentId());

        var id = paymentRepository.save(payment);

        then(paymentRepository.findById(id))
                .isEqualTo(payment);
    }

    @Test
    void should_assign_a_unique_id_to_new_payments() {
        var payment1 = new Payment(new ExternalPaymentId());
        var payment2 = new Payment(new ExternalPaymentId());
        var payment3 = new Payment(new ExternalPaymentId());

        var id1 = paymentRepository.save(payment1);
        var id2 = paymentRepository.save(payment2);
        var id3 = paymentRepository.save(payment3);
        var savedPayment1 = paymentRepository.findById(id1);
        var savedPayment2 = paymentRepository.findById(id2);
        var savedPayment3 = paymentRepository.findById(id3);

        then(savedPayment1.id()).isNotNull()
                .isNotEqualTo(savedPayment2.id())
                .isNotEqualTo(savedPayment3.id());
    }
}