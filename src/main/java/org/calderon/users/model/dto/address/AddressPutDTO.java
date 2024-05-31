package org.calderon.users.model.dto.address;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressPutDTO {
  @NotNull(message = "The address id is required to update")
  private Long id;
  private String street;
  private Integer number;
  private String city;
  private String state;
  private String country;
  private String description;
  private boolean removeDescription;
}