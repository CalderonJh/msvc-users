package org.calderon.users.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "addresses", schema = "users")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Address {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 100)
  private String street;

  @Column(nullable = false)
  private Integer number;

  @Column(nullable = false, length = 100)
  private String city;

  @Column(nullable = false, length = 100)
  private String state;

  @Column(nullable = false, length = 100)
  private String country;

  private String description;
}
