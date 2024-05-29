package org.calderon.users.model.mapper;

import org.calderon.users.model.Address;
import org.calderon.users.model.dto.AddressDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface AddressMapper {
	AddressMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(AddressMapper.class);

	AddressDTO toAddressDTO(Address address);

	@Mapping(target = "id", ignore = true)
	Address toAddress(AddressDTO addressDTO);

}
