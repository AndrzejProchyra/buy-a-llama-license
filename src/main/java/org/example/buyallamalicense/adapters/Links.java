package org.example.buyallamalicense.adapters;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.net.URI;

public record Links(@JsonProperty("next_url") Link nextUrl) {
}
