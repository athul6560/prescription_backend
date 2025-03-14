package com.zeezaglobal.prescription.Controller;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentMethod;
import com.stripe.param.PaymentMethodAttachParams;
import com.zeezaglobal.prescription.Service.StripeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @PostMapping("/subscription")
    public String createSubscription(@RequestBody Map<String, Object> payload) throws StripeException {
        return stripeService.createSubscription((String) payload.get("customerId"), (Boolean) payload.get("isMonthly"));
    }

    @PostMapping("/payment-intent")
    public Map<String, String> createPaymentIntent(@RequestBody Map<String, Object> payload) throws StripeException {
        return stripeService.createPaymentIntent((String) payload.get("customerId"), (Boolean) payload.get("isMonthly"));
    }
}