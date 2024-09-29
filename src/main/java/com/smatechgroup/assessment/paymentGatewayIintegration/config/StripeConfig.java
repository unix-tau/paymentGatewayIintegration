package com.smatechgroup.assessment.paymentGatewayIintegration.config;

import com.stripe.Stripe;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StripeConfig {
    public StripeConfig() {
        Stripe.apiKey = "your-stripe-secret-key"; // Replace with your actual key
    }
}
