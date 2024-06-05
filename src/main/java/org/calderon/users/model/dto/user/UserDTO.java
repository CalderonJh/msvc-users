package org.calderon.users.model.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import org.calderon.users.model.Course;
import org.calderon.users.model.dto.address.AddressDTO;

import java.util.List;

@Data
@Builder
public class UserDTO {

  private Long id;

  @NotBlank(message = "The name is required")
  private String name;

  @NotBlank(message = "The last name is required")
  private String lastName;

  @NotBlank(message = "The email is required")
  private String email;

  private List<AddressDTO> addresses;

  private List<Course> courses;
}