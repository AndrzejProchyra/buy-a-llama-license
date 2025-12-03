package org.example.buyallamalicense.adapters;

import org.example.buyallamalicense.adapters.in.web.WebAdapter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import static org.assertj.core.api.Assertions.assertThat;

@WebMvcTest(WebAdapter.class)
class WebAdapterTest {

    @Autowired
    private MockMvcTester mvc;

    @Test
    void should_return_a_view_with_a_reference_field_and_a_payment_button() {
        assertThat(mvc.get().uri("/").accept(MediaType.TEXT_HTML))
                .hasStatusOk()
                .hasViewName("payment")
                .bodyText()
                .contains("<h1>Buy a Llama License</h1>")
                .contains("<h2>Payment</h2>");
    }
}
