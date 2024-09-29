package com.smatechgroup.assessment.paymentGatewayIintegration.controller;

import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.smatechgroup.assessment.paymentGatewayIintegration.service.PaymentService;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/stripe")
    public ResponseEntity<?> chargeStripe(@RequestParam String token, @RequestParam double amount) {
        try {
            Charge charge = paymentService.processStripePayment(token, amount);
            return ResponseEntity.ok(charge);
        } catch (StripeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/paypal")
    public ResponseEntity<?> createPayPalPayment(@RequestParam double amount,
                                                 @RequestParam String returnUrl,
                                                 @RequestParam String cancelUrl) {
        try {
            Payment payment = paymentService.processPayPalPayment(amount, returnUrl, cancelUrl);
            return ResponseEntity.ok(payment);
        } catch (PayPalRESTException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/paypal/execute")
    public ResponseEntity<?> executePayPalPayment(@RequestParam String paymentId, @RequestParam String payerId) {
        try {
            Payment payment = paymentService.executePayPalPayment(paymentId, payerId);
            return ResponseEntity.ok(payment);
        } catch (PayPalRESTException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
