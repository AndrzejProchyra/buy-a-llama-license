package org.example.buyallamalicense.adapters.out;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Links(@JsonProperty("next_url") Link nextUrl) {
}
