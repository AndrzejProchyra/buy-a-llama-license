package org.example.buyallamalicense.adapters.out.payment;

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
        var requestPayload = new CreateCardPaymentRequest(amount, description, reference, "http://localhost:8080/status");
        var createPaymentResult = restClient.post()
                .uri(URI.create("/v1/payments"))
                .contentType(APPLICATION_JSON)
                .body(requestPayload)
                .retrieve()
                .body(CreatePaymentResult.class);
        var externalPaymentId = new ExternalPaymentId(createPaymentResult.paymentId());
        var nextUrl = URI.create(createPaymentResult.links().nextUrl().href());
        return new PaymentCreationResponse(externalPaymentId, nextUrl);
    }

    @Override
    public PaymentStatus getStatusFor(ExternalPaymentId id) {
        PaymentWithAllLinks response = restClient.get()
                .uri(URI.create("/v1/payments/" + id.id()))
                .retrieve()
                .body(PaymentWithAllLinks.class);

        if (response.state().status().equalsIgnoreCase("success")) {
            return PaymentStatus.SUCCESS;
        }
        if (response.state().status().equalsIgnoreCase("created")) {
            return PaymentStatus.CREATED;
        }
        return PaymentStatus.FAILURE;
    }
}
