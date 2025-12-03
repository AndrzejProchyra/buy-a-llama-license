package org.example.buyallamalicense.app;

import org.example.buyallamalicense.app.model.Payment;
import org.example.buyallamalicense.app.model.PaymentId;
import org.example.buyallamalicense.app.model.PaymentStatus;
import org.example.buyallamalicense.app.ports.PaymentPort;
import org.example.buyallamalicense.app.ports.PaymentRepository;
import org.example.buyallamalicense.app.ports.PaymentRequestResponse;

public class PaymentUseCase {
    private final PaymentPort paymentPort;
    private final PaymentRepository paymentRepository;

    public PaymentUseCase(PaymentPort paymentAdapter, PaymentRepository paymentRepository) {
        paymentPort = paymentAdapter;
        this.paymentRepository = paymentRepository;
    }

    public PaymentRequestResponse requestPayment(String reference, int amount) {
        var response = paymentPort.createPayment(reference, amount, "A Llama License");
        var payment = new Payment(response.paymentId());
        var paymentId = paymentRepository.save(payment);
        return new PaymentRequestResponse(paymentId, response.nextUrl());
    }

    public PaymentStatus getPaymentStatus(PaymentId paymentId) {
        var payment = paymentRepository.findById(paymentId).get();
        var externalPaymentId = payment.externalPaymentId();
        return paymentPort.getStatusFor(externalPaymentId);
    }
}
