package org.example.buyallamalicense.app.ports.payment;

import org.example.buyallamalicense.app.model.ExternalPaymentId;
import org.example.buyallamalicense.app.model.PaymentStatus;

public interface PaymentPort {
    PaymentCreationResponse createPayment(String reference, int amount, String description);

    PaymentStatus getStatusFor(ExternalPaymentId id);
}
