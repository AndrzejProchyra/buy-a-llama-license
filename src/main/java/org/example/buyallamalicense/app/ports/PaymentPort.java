package org.example.buyallamalicense.app.ports;

public interface PaymentPort {
    PaymentCreationResponse createPayment(String reference, int amount, String description);
}
