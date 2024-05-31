package org.calderon.users.model.dto.user;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

/**
 * Data transfer object to update a user personal information To update an user address, use the
 * AddressPutDTO
 */
@Data
@Builder
public class UserPutDTO {
  @NotNull(message = "The user id is required to update")
  private long id;

  private String name;

  private String lastName;

  private String email;
}
