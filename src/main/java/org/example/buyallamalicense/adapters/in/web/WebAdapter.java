package org.example.buyallamalicense.adapters.in.web;

import jakarta.servlet.http.HttpSession;
import org.example.buyallamalicense.app.PaymentUseCase;
import org.example.buyallamalicense.app.model.PaymentId;
import org.example.buyallamalicense.app.model.PaymentStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class WebAdapter {

    private final PaymentUseCase paymentUseCase;

    public WebAdapter(PaymentUseCase paymentUseCase) {
        this.paymentUseCase = paymentUseCase;
    }

    @GetMapping
    public String paymentForm(Model model) {
        model.addAttribute("payment", new PaymentForm());
        return "payment";
    }

    @PostMapping("/pay")
    public String pay(@ModelAttribute PaymentForm paymentForm, HttpSession session) {
        var reference = paymentForm.getReference();
        var paymentRequestResponse = paymentUseCase.requestPayment(reference, 4000);
        session.setAttribute("paymentId", paymentRequestResponse.paymentId());
        return "redirect:" + paymentRequestResponse.paymentUrl();
    }

    @GetMapping("/status")
    public String status(Model model, HttpSession session) {
        PaymentStatus paymentStatus = paymentUseCase.getPaymentStatus((PaymentId) session.getAttribute("paymentId"));
        model.addAttribute("status", paymentStatus);
        return "status";
    }
}
