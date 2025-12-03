package org.example.buyallamalicense.adapters.out;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateCardPaymentRequest(int amount, String description, String reference,
                                       @JsonProperty("return_url") String returnUrl) {
}
