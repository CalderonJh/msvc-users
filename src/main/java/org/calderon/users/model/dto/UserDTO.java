package org.calderon.users.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {
  @NotNull(message = "The name is required")
  private String name;

  @NotNull(message = "The last name is required")
  private String lastName;

  @NotNull(message = "The email is required")
  private String email;
}