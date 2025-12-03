package org.example.buyallamalicense.adapters.in;

import org.example.buyallamalicense.app.PaymentUseCase;
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
    public String pay(@ModelAttribute PaymentForm paymentForm) {
        var reference = paymentForm.getReference();
        var paymentRequestResponse = paymentUseCase.requestPayment(reference, 4000);
        return "redirect:" + paymentRequestResponse.paymentUrl();
    }
}
