package org.example.buyallamalicense.adapters.out.payment;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Links(@JsonProperty("next_url") Link nextUrl) {
}
