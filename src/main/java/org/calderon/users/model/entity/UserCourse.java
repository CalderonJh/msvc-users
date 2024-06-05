package org.calderon.users.model.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_course", schema = "users")
public class UserCourse {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "id_user")
  private User user;

  @Column(name = "id_course")
  private String idCourse;
}
