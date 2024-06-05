package org.calderon.users.model.mapper;

import org.calderon.users.model.entity.Address;
import org.calderon.users.model.dto.address.AddressDTO;
import org.mapstruct.Mapper;

@Mapper
public interface AddressMapper {
	AddressMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(AddressMapper.class);

	AddressDTO toAddressDTO(Address address);

	Address toAddress(AddressDTO addressDTO);

}
