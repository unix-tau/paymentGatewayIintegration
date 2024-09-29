package com.smatechgroup.assessment.paymentGatewayIintegration.config;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import com.paypal.base.rest.PayPalResource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PayPalConfig {

    @Value("${paypal.client.id}")
    private String clientId;

    @Value("${paypal.client.secret}")
    private String clientSecret;

    @Value("${paypal.mode}")
    private String mode; // "sandbox" or "live"

    @Bean
    public APIContext apiContext() {
        APIContext apiContext = new APIContext(clientId, clientSecret, mode);
        return apiContext;
    }
}

