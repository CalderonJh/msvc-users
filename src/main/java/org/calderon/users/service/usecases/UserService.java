package org.calderon.users.service.usecases;

import org.calderon.users.model.User;
import org.calderon.users.model.dto.UserDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {
  String createUser(UserDTO userDTO);

  String updateUser(UserDTO userDTO);

  void deleteUser(Long id);

  Optional<User> getUser(Long id);

  List<User> getUsers();
}
