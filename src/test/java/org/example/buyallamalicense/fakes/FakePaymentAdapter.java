package org.example.buyallamalicense.fakes;

import org.example.buyallamalicense.app.model.ExternalPaymentId;
import org.example.buyallamalicense.app.ports.PaymentCreationResponse;
import org.example.buyallamalicense.app.ports.PaymentPort;

public class FakePaymentAdapter implements PaymentPort {
    public void beSuccessful() {

    }

    @Override
    public PaymentCreationResponse createPayment(String reference, int amount, String description) {
        return new PaymentCreationResponse(new ExternalPaymentId());
    }
}
