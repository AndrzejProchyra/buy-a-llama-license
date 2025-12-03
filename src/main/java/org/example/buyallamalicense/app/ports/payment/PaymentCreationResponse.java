package org.example.buyallamalicense.app.ports.payment;

import org.example.buyallamalicense.app.model.ExternalPaymentId;

public record PaymentCreationResponse(ExternalPaymentId paymentId, java.net.URI nextUrl) {

}
