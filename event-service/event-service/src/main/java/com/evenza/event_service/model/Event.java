package com.evenza.event_service.model;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table (name = "events")
public class Event {
  @Id
  @GeneratedValue (strategy = GenerationType.IDENTITY)
    private  int id ;

    private  String  title;
    private  String description;
    private  String date;
    private  String  venue;
    private int capacity;
    private double price;
}

