package com.elearning.service;

import com.razorpay.RazorpayException;
import org.json.JSONObject;

public interface PaymentService {

    JSONObject createOrder(Long courseId, String username)
            throws RazorpayException;

    boolean verifyPayment(Long courseId,
                          String username,
                          String orderId,
                          String paymentId,
                          String signature);
}