package org.calderon.users.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {
  @NotBlank(message = "The name is required")
  private String name;

  @NotBlank(message = "The last name is required")
  private String lastName;

  @NotBlank(message = "The email is required")
  private String email;
}
