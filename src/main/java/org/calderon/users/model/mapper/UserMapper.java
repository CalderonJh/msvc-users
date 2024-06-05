package org.calderon.users.model.mapper;

import org.calderon.users.model.entity.User;
import org.calderon.users.model.dto.user.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
  UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

  UserDTO toUserDTO(User user);

  @Mapping(target = "courses", ignore = true)
  User toUser(UserDTO userDTO);
}