package org.example.buyallamalicense.adapters;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateCardPaymentRequest(int amount, String description, String reference,
                                       @JsonProperty("return_url") String returnUrl) {
}
