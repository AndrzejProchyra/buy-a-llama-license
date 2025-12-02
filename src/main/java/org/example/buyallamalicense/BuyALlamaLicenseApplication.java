package org.example.buyallamalicense;

import org.example.buyallamalicense.adapters.FakePaymentAdapter;
import org.example.buyallamalicense.adapters.InMemoryPaymentRepository;
import org.example.buyallamalicense.app.PaymentUseCase;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BuyALlamaLicenseApplication {

    static void main(String[] args) {
        SpringApplication.run(BuyALlamaLicenseApplication.class, args);
    }

    @Bean
    PaymentUseCase paymentUseCase() {
        return new PaymentUseCase(new FakePaymentAdapter(), new InMemoryPaymentRepository());
    }
}
