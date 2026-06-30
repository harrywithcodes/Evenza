package com.evenza.payment_service.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table (name = "payments")
public class Payment {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;

    private int registrationId;
    private double amount;
    private String status;

    private String razorpayOrderId;
    private String razorpayPaymentId;
}
