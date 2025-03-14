package com.zeezaglobal.prescription.Service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.PaymentMethod;
import com.stripe.model.Subscription;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.CustomerUpdateParams;
import com.stripe.param.PaymentMethodAttachParams;
import com.stripe.param.SubscriptionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



@Service
public class StripeService {

    @Value("${stripe.secret-key}")
    private String stripeSecretKey;

    @Value("${stripe.monthly-price-id}")
    private String monthlyPriceId;

    @Value("${stripe.yearly-price-id}")
    private String yearlyPriceId;

    public String createCustomer(String email) throws StripeException {
        Stripe.apiKey = stripeSecretKey;
        CustomerCreateParams params = CustomerCreateParams.builder()
                .setEmail(email)

                .build();
        Customer customer = Customer.create(params);
        return customer.getId();
    }


    public void attachPaymentMethod(Map<String, String> payload) throws StripeException {
        // Set the Stripe API key
        Stripe.apiKey = stripeSecretKey;

        // Retrieve the payment method using the ID from the request payload
        PaymentMethod paymentMethod = PaymentMethod.retrieve(payload.get("paymentMethodId"));
        Customer customer = Customer.retrieve(payload.get("customerId"));
        // Attach the payment method to the customer
        paymentMethod.attach(PaymentMethodAttachParams.builder()
                .setCustomer(payload.get("customerId"))
                .build());
        CustomerUpdateParams params = CustomerUpdateParams.builder()
                .setInvoiceSettings(CustomerUpdateParams.InvoiceSettings.builder()
                        .setDefaultPaymentMethod(payload.get("paymentMethodId"))
                        .build())
                .build();
        Customer updatedCustomer = customer.update(params);
    }
    public String createSubscription(String customerId, boolean isMonthly) throws StripeException {
        Stripe.apiKey = stripeSecretKey;
        String priceId = isMonthly ? monthlyPriceId : yearlyPriceId;

        SubscriptionCreateParams params = SubscriptionCreateParams.builder()
                .setCustomer(customerId)
                .addItem(SubscriptionCreateParams.Item.builder().setPrice(priceId).build())
                .build();

        Subscription subscription = Subscription.create(params);
        return subscription.getId();
    }

    public Map<String, String> createPaymentIntent(String customerId, boolean isMonthly) throws StripeException {
        Stripe.apiKey = stripeSecretKey;
        String priceId = isMonthly ? monthlyPriceId : yearlyPriceId;

        Map<String, Object> params = new HashMap<>();
        params.put("customer", customerId);
        params.put("automatic_payment_methods", Map.of("enabled", true));
        params.put("items", List.of(Map.of("price", priceId)));

        com.stripe.model.PaymentIntent paymentIntent = com.stripe.model.PaymentIntent.create(params);
        Map<String, String> responseData = new HashMap<>();
        responseData.put("clientSecret", paymentIntent.getClientSecret());
        responseData.put("paymentIntentId", paymentIntent.getId());
        return responseData;
    }
}
