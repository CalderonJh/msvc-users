package org.calderon.users.model.dto.user;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.calderon.users.model.dto.AddressDTO;

@Data
@Builder
public class UserPutDTO {
	@NotNull(message = "The id is required for update")
	private Long id;
	private String name;
	private String lastName;
	private String email;
	private AddressDTO[] addresses;
}