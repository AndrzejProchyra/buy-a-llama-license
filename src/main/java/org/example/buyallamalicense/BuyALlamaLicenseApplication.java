package org.example.buyallamalicense;

import org.example.buyallamalicense.adapters.out.FakePaymentAdapter;
import org.example.buyallamalicense.adapters.out.GovUkAdapter;
import org.example.buyallamalicense.adapters.out.InMemoryPaymentRepository;
import org.example.buyallamalicense.app.PaymentUseCase;
import org.example.buyallamalicense.app.ports.PaymentPort;
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
    public PaymentPort paymentPort() {
        return new GovUkAdapter(URI.create("http://localhost:8080"), "");
    }

    @Bean
    @Profile("test")
    public PaymentPort testPaymentPort() {
        return new FakePaymentAdapter();
    }

    @Bean
    PaymentUseCase paymentUseCase() {
        return new PaymentUseCase(new FakePaymentAdapter(), new InMemoryPaymentRepository());
    }
}
