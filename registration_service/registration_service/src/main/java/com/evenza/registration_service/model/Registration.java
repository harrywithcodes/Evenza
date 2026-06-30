package com.evenza.registration_service.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity

@Table (name = "registrations")
public class Registration {
@Id
@GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;
    private int eventId;
    private  int userId;
    private  String status;

}
