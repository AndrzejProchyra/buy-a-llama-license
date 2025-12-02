package org.example.buyallamalicense.app;

import org.example.buyallamalicense.app.model.ExternalPaymentId;
import org.example.buyallamalicense.app.model.Payment;
import org.example.buyallamalicense.app.model.PaymentId;
import org.example.buyallamalicense.app.ports.PaymentPort;
import org.example.buyallamalicense.app.ports.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.then;
import static org.example.buyallamalicense.app.model.PaymentStatus.SUCCESS;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class PaymentUseCaseTest {

    private PaymentUseCase paymentUseCase;
    private PaymentPort paymentPortStub;
    private PaymentRepository paymentRepositoryStub;

    @BeforeEach
    void setUp() {
        paymentPortStub = mock(PaymentPort.class);
        paymentRepositoryStub = mock(PaymentRepository.class);
        paymentUseCase = new PaymentUseCase(paymentPortStub, paymentRepositoryStub);
    }

    @Test
    void should_get_payment_status_via_payment_port() {
        var paymentId = new PaymentId(42);
        var externalPaymentId = new ExternalPaymentId("external-id");
        var payment = new Payment(paymentId, externalPaymentId);
        given(paymentRepositoryStub.findById(paymentId))
                .willReturn(Optional.of(payment));
        given(paymentPortStub.getStatusFor(externalPaymentId))
                .willReturn(SUCCESS);

        var status = paymentUseCase.getPaymentStatus(paymentId);

        then(status)
                .isEqualTo(SUCCESS);
    }
}