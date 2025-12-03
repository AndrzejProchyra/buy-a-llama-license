package org.example.buyallamalicense.adapters;

import org.example.buyallamalicense.adapters.out.InMemoryPaymentRepository;
import org.example.buyallamalicense.app.model.ExternalPaymentId;
import org.example.buyallamalicense.app.model.Payment;
import org.example.buyallamalicense.app.model.PaymentId;
import org.example.buyallamalicense.app.ports.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

class InMemoryPaymentRepositoryTest {

    public static final ExternalPaymentId EXTERNAL_PAYMENT_ID = new ExternalPaymentId("external-id");
    private PaymentRepository paymentRepository;

    @BeforeEach
    void setUp() {
        paymentRepository = new InMemoryPaymentRepository();
    }

    @Test
    void should_save_and_find_a_payment() {
        var paymentId = new PaymentId(42);
        var payment = new Payment(paymentId, EXTERNAL_PAYMENT_ID);

        var id = paymentRepository.save(payment);

        then(paymentRepository.findById(id))
                .contains(payment);
    }

    @Test
    void should_assign_a_unique_id_to_new_payments() {
        var payment1 = new Payment(EXTERNAL_PAYMENT_ID);
        var payment2 = new Payment(EXTERNAL_PAYMENT_ID);
        var payment3 = new Payment(EXTERNAL_PAYMENT_ID);

        var id1 = paymentRepository.save(payment1);
        var id2 = paymentRepository.save(payment2);
        var id3 = paymentRepository.save(payment3);
        var savedPayment1 = paymentRepository.findById(id1);
        var savedPayment2 = paymentRepository.findById(id2);
        var savedPayment3 = paymentRepository.findById(id3);

        then(savedPayment1).isPresent();
        then(savedPayment2).isPresent();
        then(savedPayment3).isPresent();
        then(savedPayment1).get()
                .extracting(Payment::id)
                .isNotNull()
                .isNotEqualTo(savedPayment2.get().id())
                .isNotEqualTo(savedPayment3.get().id());
    }

    @Test
    void should_persist_payment_id_between_saves() {
        var paymentId = new PaymentId(42);
        var payment = new Payment(paymentId, EXTERNAL_PAYMENT_ID);

        paymentRepository.save(payment);
        var savedPayment = paymentRepository.findById(paymentId);

        then(savedPayment).isPresent()
                .get().extracting(Payment::id)
                .isEqualTo(paymentId);
    }
}