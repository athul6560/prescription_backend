package com.zeezaglobal.prescription.Service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.*;
import com.stripe.net.RequestOptions;
import com.stripe.param.*;
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

    public Map<String, String> createSubscriptionIntent(String customerId, boolean isMonthly) throws StripeException {
        Stripe.apiKey = stripeSecretKey;
        String priceId = isMonthly ? monthlyPriceId : yearlyPriceId;

        // Create subscription
        SubscriptionCreateParams params = SubscriptionCreateParams.builder()
                .setCustomer(customerId)
                .addItem(SubscriptionCreateParams.Item.builder().setPrice(priceId).build())
                .setPaymentBehavior(SubscriptionCreateParams.PaymentBehavior.DEFAULT_INCOMPLETE) // Ensures user completes payment
                .build();

        // Create the subscription
        Subscription subscription = Subscription.create(params);

        // Expand `latest_invoice.payment_intent` by retrieving the subscription with expansions
        RequestOptions requestOptions = RequestOptions.builder().build();
        Subscription retrievedSubscription = Subscription.retrieve(subscription.getId(),
                Map.of("expand", List.of("latest_invoice.payment_intent")), requestOptions);

        // Get the PaymentIntent associated with the subscription
        String clientSecret = retrievedSubscription.getLatestInvoiceObject().getPaymentIntentObject().getClientSecret();

        // **Create SetupIntent for saving payment method**
        SetupIntentCreateParams setupIntentParams = SetupIntentCreateParams.builder()
                .setCustomer(customerId) // Set customer ID
                .setPaymentMethod(retrievedSubscription.getLatestInvoiceObject().getPaymentIntentObject().getPaymentMethod()) // Use the payment method from the payment intent
                .build();

        // Create the SetupIntent
        SetupIntent setupIntent = SetupIntent.create(setupIntentParams);

        // Generate an ephemeral key for the customer
        EphemeralKeyCreateParams ephemeralKeyParams = EphemeralKeyCreateParams.builder()
                .setCustomer(customerId)
                .setStripeVersion("2023-10-16") // Required for ephemeral keys
                .build();
        EphemeralKey ephemeralKey = EphemeralKey.create(ephemeralKeyParams);

        // Prepare response
        Map<String, String> responseData = new HashMap<>();
        responseData.put("subscriptionId", subscription.getId());
        responseData.put("clientSecret", setupIntent.getClientSecret());  // Use the SetupIntent client secret here
        responseData.put("ephemeralKey", ephemeralKey.getSecret());

        return responseData;
    }
}
