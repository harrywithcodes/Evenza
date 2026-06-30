package com.evenza.notification_service.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table (name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int userid;
    private String message;
    private String status;
}
