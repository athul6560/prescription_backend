package com.zeezaglobal.prescription.Controller;

import com.stripe.exception.StripeException;
import com.stripe.model.SetupIntent;
import com.stripe.param.SetupIntentCreateParams;
import com.zeezaglobal.prescription.Service.StripeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/stripe")
public class StripeController {

    @Autowired
    private StripeService stripeService;

    @PostMapping("/attach-payment-method")
    public void attachPaymentMethod(@RequestBody Map<String, String> payload) throws StripeException {
        stripeService.attachPaymentMethod(payload);
    }



    @PostMapping("/setup-intent")
    public ResponseEntity<Map<String, String>> createSetupIntent(@RequestBody Map<String, String> request) {
        try {
            // Extract customerId and isMonthly from the request body
            String customerId = request.get("doctorId");


            // Call the service to create the payment intent
            String clientSecret = stripeService.createPaymentIntent(Long.valueOf(customerId));

            // Create a response map with the client secret
            Map<String, String> response = new HashMap<>();
            response.put("clientSecret", clientSecret);

            // Return the client secret in the response
            return ResponseEntity.ok(response);

        } catch (StripeException e) {
            // Handle Stripe exceptions and return an error response
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Error creating payment intent: " + e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }
}