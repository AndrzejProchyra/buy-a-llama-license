package org.example.buyallamalicense.app;

import org.example.buyallamalicense.app.model.ExternalPaymentId;
import org.example.buyallamalicense.app.model.Payment;
import org.example.buyallamalicense.app.model.PaymentId;
import org.example.buyallamalicense.app.ports.payment.PaymentCreationResponse;
import org.example.buyallamalicense.app.ports.payment.PaymentPort;
import org.example.buyallamalicense.app.ports.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.then;
import static org.example.buyallamalicense.app.model.PaymentStatus.SUCCESS;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class PaymentUseCaseTest {

    public static final URI ANY_URL = URI.create("http://example.org");
    public static final ExternalPaymentId ANY_PAYMENT_ID = new ExternalPaymentId("external-id");
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

    @Test
    void should_return_payment_id_assigned_by_the_repository() {
        var paymentId = new PaymentId(42);
        given(paymentPortStub.createPayment(any(), anyInt(), any()))
                .willReturn(new PaymentCreationResponse(ANY_PAYMENT_ID, ANY_URL));
        given(paymentRepositoryStub.save(any()))
                .willReturn(paymentId);

        var paymentRequestResponse = paymentUseCase.requestPayment("some-reference", 123);

        then(paymentRequestResponse.paymentId()).isEqualTo(paymentId);
    }

    @Test
    void should_return_payment_url_from_the_payment_port() {
        var expectedPaymentUrl = URI.create("http://example.org");
        given(paymentPortStub.createPayment(any(), anyInt(), any()))
                .willReturn(new PaymentCreationResponse(ANY_PAYMENT_ID, expectedPaymentUrl));

        var response = paymentUseCase.requestPayment("some-reference", 123);

        then(response.paymentUrl())
                .isEqualTo(expectedPaymentUrl);
    }
}