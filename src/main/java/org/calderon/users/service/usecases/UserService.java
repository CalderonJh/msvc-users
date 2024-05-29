package org.calderon.users.service.usecases;

import org.calderon.users.model.User;
import org.calderon.users.model.dto.user.UserDTO;
import org.calderon.users.model.dto.user.UserPatchDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
  String create(UserDTO userDTO);

  String updateUser(UserPatchDTO userDTO);

  String deleteUser(Long id);

  User getUser(Long id);

  Page<User> getUsers(Pageable pageable);
}
