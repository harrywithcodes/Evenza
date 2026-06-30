package com.evenza.payment_service.service;

import com.evenza.payment_service.repository.PaymentRepository;
import com.evenza.payment_service.model.Payment;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

        @Service
        public class PaymentService {

            @Autowired
            private PaymentRepository paymentRepository;

            @Autowired
            private RazorpayClient razorpayClient;

            // ===== Existing CRUD methods (as-is) =====
            public List<Payment> getAllPayments() {
                return paymentRepository.findAll();
            }

            public Payment CreatePayments(Payment p) {
                return paymentRepository.save(p);
            }

            public void DeletePayments(int id) {
                paymentRepository.deleteById(id);
            }

            // ===== Razorpay methods (new) =====
            // Step 1: Create Razorpay Order

            public Order createOrder(double amount, int registrationId) throws Exception {
                JSONObject orderRequest = new JSONObject();
                orderRequest.put("amount", (int) (amount * 100)); // Razorpay needs paise
                orderRequest.put("currency", "INR");
                orderRequest.put("receipt", "reg_" + registrationId);

                Order order = razorpayClient.orders.create(orderRequest);

                // Save initial payment record with status "CREATED"

                Payment payment = new Payment();
                payment.setRegistrationId(registrationId);
                payment.setAmount(amount);
                payment.setStatus("CREATED");
                payment.setRazorpayOrderId(order.get("id").toString());
                paymentRepository.save(payment);

                return order;
            }

            // Step 2: Verify and update payment after success

            public Payment verifyAndSavePayment(String razorpayOrderId, String razorpayPaymentId, String status) {
                Payment payment = paymentRepository.findAll().stream()
                        .filter(p -> p.getRazorpayOrderId().equals(razorpayOrderId))
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("Payment record not found for order: " + razorpayOrderId));
                payment.setRazorpayPaymentId(razorpayPaymentId);
                payment.setStatus(status);
                return paymentRepository.save(payment);

            }

        }

