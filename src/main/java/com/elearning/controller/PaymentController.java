package com.elearning.controller;

import com.elearning.service.PaymentService;
import com.razorpay.RazorpayException;

import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/create-order/{courseId}")
    public ResponseEntity<?> createOrder(
            @PathVariable Long courseId,
            Authentication authentication) {

        try {
            JSONObject order = paymentService.createOrder(
                    courseId,
                    authentication.getName()
            );

            return ResponseEntity.ok(order.toMap());

        } catch (RazorpayException e) {
            return ResponseEntity.internalServerError()
                    .body("Unable to create payment order");
        }
    }

    @PostMapping("/verify/{courseId}")
    public ResponseEntity<?> verifyPayment(
            @PathVariable Long courseId,
            @RequestParam String orderId,
            @RequestParam String paymentId,
            @RequestParam String signature,
            Authentication authentication) {

        boolean verified = paymentService.verifyPayment(
                courseId,
                authentication.getName(),
                orderId,
                paymentId,
                signature
        );

        if (verified) {
            return ResponseEntity.ok(
                    "Payment verified successfully and enrollment activated!"
            );
        }

        return ResponseEntity.badRequest()
                .body("Payment verification failed!");
    }
}