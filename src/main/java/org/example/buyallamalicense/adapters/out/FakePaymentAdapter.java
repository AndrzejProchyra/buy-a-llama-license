package org.example.buyallamalicense.adapters.out;

import org.example.buyallamalicense.app.model.ExternalPaymentId;
import org.example.buyallamalicense.app.model.PaymentStatus;
import org.example.buyallamalicense.app.ports.PaymentCreationResponse;
import org.example.buyallamalicense.app.ports.PaymentPort;

import java.net.URI;

import static org.example.buyallamalicense.app.model.PaymentStatus.FAILURE;
import static org.example.buyallamalicense.app.model.PaymentStatus.SUCCESS;

public class FakePaymentAdapter implements PaymentPort {
    private boolean isSuccessful;

    public void beSuccessful() {
        isSuccessful = true;
    }

    @Override
    public PaymentCreationResponse createPayment(String reference, int amount, String description) {
        return new PaymentCreationResponse(new ExternalPaymentId("external-id"), URI.create("http://example.com"));
    }

    @Override
    public PaymentStatus getStatusFor(ExternalPaymentId id) {
        return isSuccessful ? SUCCESS : FAILURE;
    }
}
