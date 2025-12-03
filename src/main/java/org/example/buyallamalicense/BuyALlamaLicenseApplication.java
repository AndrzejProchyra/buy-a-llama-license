package org.example.buyallamalicense;

import org.example.buyallamalicense.adapters.out.payment.FakePaymentAdapter;
import org.example.buyallamalicense.adapters.out.payment.GovUkAdapter;
import org.example.buyallamalicense.adapters.out.repository.InMemoryPaymentRepository;
import org.example.buyallamalicense.app.PaymentUseCase;
import org.example.buyallamalicense.app.ports.PaymentPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.net.URI;

@SpringBootApplication
public class BuyALlamaLicenseApplication {

    static void main(String[] args) {
        SpringApplication.run(BuyALlamaLicenseApplication.class, args);
    }

    @Bean
    @Profile("production")
    public PaymentPort paymentAdapter(@Value("${gov-pay.base-url}") String baseUrl, @Value("${gov-pay.api-key}") String apiKey) {
        return new GovUkAdapter(URI.create(baseUrl), apiKey);
    }

    @Bean
    @Profile("test")
    public PaymentPort testPaymentAdapter() {
        return new FakePaymentAdapter();
    }

    @Bean
    PaymentUseCase paymentUseCase(PaymentPort paymentAdapter) {
        return new PaymentUseCase(paymentAdapter, new InMemoryPaymentRepository());
    }
}
