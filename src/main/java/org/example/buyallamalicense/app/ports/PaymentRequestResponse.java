package org.example.buyallamalicense.app.ports;

import org.example.buyallamalicense.app.model.PaymentId;

import java.net.URI;

public record PaymentRequestResponse(PaymentId paymentId, URI paymentUrl) {

}
