package org.example.buyallamalicense.adapters.out;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreatePaymentResult(@JsonProperty("payment_id") String paymentId, @JsonProperty("_links") Links links) {

}
