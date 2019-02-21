package org.ivanov.Domain.entity;

import javax.persistence.*;

@Entity
public class Authorities {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String email;
  private String authority = "ROLE_Admin";

  @OneToOne
  private Person person;

  public Authorities() {

  }
  public Authorities(String email, String status, int id) {
    this.email = email;
    authority = status;
    this.id = id;
  }
}
