package org.example.buyallamalicense.app.model;

public record Payment(PaymentId id, ExternalPaymentId externalPaymentId) {
    public Payment(ExternalPaymentId externalPaymentId) {
        this(null, externalPaymentId);
    }

    public Payment withId(PaymentId id) {
        return new Payment(id, externalPaymentId);
    }
}
