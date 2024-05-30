package org.calderon.users.model.dto.address;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddressPutDTO {
  @NotNull(message = "The address id is required to update")
  private Long id;
  private String street;
  private int number;
  private String city;
  private String state;
  private String country;
  private String description;
}