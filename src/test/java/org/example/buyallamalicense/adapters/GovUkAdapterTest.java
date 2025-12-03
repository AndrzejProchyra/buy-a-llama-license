package org.example.buyallamalicense.adapters;

import org.example.buyallamalicense.adapters.out.payment.GovUkAdapter;
import org.example.buyallamalicense.app.model.ExternalPaymentId;
import org.example.buyallamalicense.app.ports.payment.PaymentCreationResponse;
import org.example.buyallamalicense.app.ports.payment.PaymentPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockserver.client.MockServerClient;
import org.mockserver.junit.jupiter.MockServerExtension;
import org.mockserver.junit.jupiter.MockServerSettings;
import org.mockserver.mock.OpenAPIExpectation;

import java.net.URI;

import static org.assertj.core.api.BDDAssertions.then;
import static org.example.buyallamalicense.app.model.PaymentStatus.CREATED;

@ExtendWith(MockServerExtension.class)
@MockServerSettings(ports = {8081})
class GovUkAdapterTest {

    private PaymentPort adapter;

    @BeforeEach
    void setUp(MockServerClient mockServerClient) {
        var baseUri = URI.create("http://localhost:8081");
        mockServerClient.upsert(OpenAPIExpectation.openAPIExpectation("pay-api.json"));
        adapter = new GovUkAdapter(baseUri, "test-api-key");
    }

    @Test
    void should_call_external_service_and_return_response() {
        var expectedResponse = new PaymentCreationResponse(
                new ExternalPaymentId("hu20sqlact5260q2nanm0q8u93"),
                URI.create("https://an.example.link/from/payment/platform"));

        var response = adapter.createPayment("foo", 123, "a llama");

        then(response).isEqualTo(expectedResponse);
    }

    @Test
    void should_get_payment_status_from_external_service() {
        var response = adapter.getStatusFor(new ExternalPaymentId("some-id"));

        then(response)
                .isEqualTo(CREATED);
    }
}
