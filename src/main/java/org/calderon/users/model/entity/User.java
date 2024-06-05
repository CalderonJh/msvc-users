package org.calderon.users.model.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.calderon.users.model.Course;

@Entity
@Data
@AllArgsConstructor
@Builder
@Table(name = "users", schema = "users")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(name = "last_name", nullable = false)
  private String lastName;

  @Column(unique = true)
  private String email;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
  @JoinColumn(name = "id_user")
  private List<Address> addresses;

  @Transient private List<Course> courses;

  public User() {
    this.addresses = new ArrayList<>();
    this.courses = new ArrayList<>();
  }

  public void addAddress(Address address) {
    this.getAddresses().add(address);
  }

  public List<Address> getAddresses() {
    if (addresses == null) {
      addresses = new ArrayList<>();
    }
    return addresses;
  }

  public List<Course> getCourses() {
    if (courses == null) {
      courses = new ArrayList<>();
    }
    return courses;
  }

  public void addCourse(Course course) {
    this.getCourses().add(course);
  }
}
