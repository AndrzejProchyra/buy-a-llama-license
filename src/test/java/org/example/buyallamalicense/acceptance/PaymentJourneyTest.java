package org.example.buyallamalicense.acceptance;

import org.example.buyallamalicense.adapters.out.InMemoryPaymentRepository;
import org.example.buyallamalicense.app.PaymentUseCase;
import org.example.buyallamalicense.app.ports.PaymentRepository;
import org.example.buyallamalicense.adapters.out.FakePaymentAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;
import static org.example.buyallamalicense.app.model.PaymentStatus.SUCCESS;

class PaymentJourneyTest {

    private FakePaymentAdapter fakePaymentAdapter;
    private PaymentUseCase paymentUseCase;
    private PaymentRepository inMemoryPaymentRepository;

    @BeforeEach
    void setUp() {
        fakePaymentAdapter = new FakePaymentAdapter();
        inMemoryPaymentRepository = new InMemoryPaymentRepository();
        paymentUseCase = new PaymentUseCase(fakePaymentAdapter, inMemoryPaymentRepository);
    }

    @Test
    void successful_payment() {
        fakePaymentAdapter.beSuccessful();

        var response = paymentUseCase.requestPayment("THX1138", 4000);

        then(paymentUseCase.getPaymentStatus(response.paymentId()))
                .isEqualTo(SUCCESS);
    }
}
