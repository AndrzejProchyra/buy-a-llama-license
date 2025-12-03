package org.example.buyallamalicense.adapters;

import org.example.buyallamalicense.app.model.ExternalPaymentId;
import org.example.buyallamalicense.app.model.PaymentStatus;
import org.example.buyallamalicense.app.ports.PaymentCreationResponse;
import org.example.buyallamalicense.app.ports.PaymentPort;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestClient;

import java.net.URI;

import static org.springframework.http.MediaType.APPLICATION_JSON;

public class GovUkAdapter implements PaymentPort {

    private final RestClient restClient;

    public GovUkAdapter(URI baseUri, String apiKey) {
        restClient = RestClient.builder()
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .baseUrl(baseUri)
                .build();
    }

    @Override
    public PaymentCreationResponse createPayment(String reference, int amount, String description) {
        var requestPayload = new CreateCardPaymentRequest(amount, description, reference, "http://foo");
        CreatePaymentResult createPaymentResult = restClient.post()
                .uri(URI.create("/v1/payments"))
                .contentType(APPLICATION_JSON)
                .body(requestPayload)
                .retrieve()
                .body(CreatePaymentResult.class);
        ExternalPaymentId externalPaymentId = new ExternalPaymentId(createPaymentResult.paymentId());
        URI nextUrl = URI.create(createPaymentResult.links().nextUrl().href());
        return new PaymentCreationResponse(externalPaymentId, nextUrl);
    }

    @Override
    public PaymentStatus getStatusFor(ExternalPaymentId id) {
        return null;
    }
}
