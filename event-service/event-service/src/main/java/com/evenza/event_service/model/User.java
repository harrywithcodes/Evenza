package com.evenza.event_service.model;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
@Table (name = "users")
public class User {

@Id
@GeneratedValue (strategy = GenerationType.IDENTITY)
  private int id ;

  private String name;
  private  String email;
  private  String password;
  private  String role;

}
