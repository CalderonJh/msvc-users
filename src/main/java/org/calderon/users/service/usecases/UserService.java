package org.calderon.users.service.usecases;

import org.calderon.users.model.User;
import org.calderon.users.model.dto.user.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
  String create(UserDTO userDTO);

  String updateUser(UserDTO userDTO);

  void deleteUser(Long id);

  User getUser(Long id);

  Page<User> getUsers(Pageable pageable);
}
