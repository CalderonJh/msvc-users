package org.calderon.users.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressDTO {
  @NotBlank(message = "The street is required")
  private String street;

  @NotNull(message = "The number is required")
  private int number;

  @NotBlank(message = "The city is required")
  private String city;

  @NotBlank(message = "The state is required")
  private String state;

  @NotBlank(message = "The country is required")
  private String country;

  private String description;
}
