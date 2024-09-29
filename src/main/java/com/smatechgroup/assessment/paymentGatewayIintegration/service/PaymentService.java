package com.smatechgroup.assessment.paymentGatewayIintegration.service;

import com.stripe.model.Charge;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.exception.StripeException;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Autowired
    private APIContext apiContext;

    public Charge processStripePayment(String token, double amount) throws StripeException {
        PaymentIntentCreateParams params =
            PaymentIntentCreateParams.builder()
                .setAmount((long) (amount * 100)) // Amount in cents
                .setCurrency("usd")
                .setPaymentMethod(token)
                .setConfirm(true)
                .build();

        return Charge.create(params);
    }

    public Payment processPayPalPayment(double amount, String returnUrl, String cancelUrl) throws PayPalRESTException {
        // Create payment
        Payment payment = new Payment();
        payment.setIntent("sale");
        // ... (set up the payment details, payer, etc.)

        // Create payment on PayPal
        return payment.create(apiContext);
    }

    public Payment executePayPalPayment(String paymentId, String payerId) throws PayPalRESTException {
        Payment payment = new Payment();
        payment.setId(paymentId);

        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(payerId);

        return payment.execute(apiContext, paymentExecution);
    }
}
