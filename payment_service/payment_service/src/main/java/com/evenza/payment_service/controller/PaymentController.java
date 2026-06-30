package com.evenza.payment_service.controller;

import com.evenza.payment_service.model.Payment;
import com.evenza.payment_service.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import com.razorpay.Order;

import java.util.List;
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @GetMapping
    public List<Payment> getAllPayment() {
        return paymentService.getAllPayments();
    }

    @PostMapping
    public Payment CreatePayment(@RequestBody Payment p) {
        return paymentService.CreatePayments(p);
    }

    @DeleteMapping("/{id}")
    public void DeletePayment(@PathVariable int id) {
        paymentService.DeletePayments(id);
    }

    @GetMapping("/count")
    public long getPaymentCount() {
        return paymentService.getAllPayments().size();
    }

    // ===== Razorpay APIs (new) =====

    // 1. Create Razorpay Order
    @PostMapping("/create-order")
    public Map<String, Object> createOrder(@RequestBody Map<String, Object> request) throws Exception {
        double amount = Double.parseDouble(request.get("amount").toString());
        int registrationId = Integer.parseInt(request.get("registrationId").toString());

        Order order = paymentService.createOrder(amount, registrationId);

        return Map.of(
                "orderId", order.get("id").toString(),
                "amount", order.get("amount").toString(),
                "currency", order.get("currency").toString()
        );
    }
// 2. Verify Payment after success

    @PostMapping("/verify")
    public Payment verifyPayment(@RequestBody Map<String, String> request) {
        String razorpayOrderId = request.get("razorpay_order_id");
        String razorpayPaymentId = request.get("razorpay_payment_id");

        return paymentService.verifyAndSavePayment(razorpayOrderId, razorpayPaymentId, "SUCESS");
    }
}