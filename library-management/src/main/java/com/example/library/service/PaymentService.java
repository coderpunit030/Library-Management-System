package com.example.library.service;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class PaymentService {

    private final RazorpayClient client;

    @Value("${app.currency}")
    private String currency;

    @Value("${razorpay.key.secret}")
    private String keySecret;

    public PaymentService(@Value("${razorpay.key.id}") String keyId,
                          @Value("${razorpay.key.secret}") String keySecret) throws Exception {
        this.client = new RazorpayClient(keyId, keySecret);
        this.keySecret = keySecret;
    }

    public Order createOrder(int amountPaise, String receiptId) throws Exception {
        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", amountPaise); // use parameter
        orderRequest.put("currency", currency);
        orderRequest.put("receipt", receiptId);
        orderRequest.put("payment_capture", 1);

        Order order = client.orders.create(orderRequest); // lowercase 'orders'
        System.out.println("Created order: " + order);
        return order;
    }

    // Verify payment signature from checkout success
    public boolean verifyCheckoutSignature(String orderId, String paymentId, String signature) throws Exception {
        String payload = orderId + "|" + paymentId;
        String expected = hmacSha256(payload, keySecret);
        return expected.equals(signature);
    }

    // Verify webhook signature
    public boolean verifyWebhookSignature(String rawBody, String signature, String webhookSecret) throws Exception {
        String expected = hmacSha256(rawBody, webhookSecret);
        return expected.equals(signature);
    }

    private String hmacSha256(String data, String secret) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
        byte[] raw = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(raw);
    }
}
