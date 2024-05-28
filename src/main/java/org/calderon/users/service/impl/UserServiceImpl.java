package org.calderon.users.service.impl;

import static org.calderon.users.tool.ITool.message;

import java.util.List;
import java.util.Optional;
import org.calderon.users.exception.ValidationException;
import org.calderon.users.model.User;
import org.calderon.users.model.dto.UserDTO;
import org.calderon.users.model.mapper.UserMapper;
import org.calderon.users.repository.UserRepository;
import org.calderon.users.service.usecases.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import org.calderon.

@Service
public class UserServiceImpl implements UserService {
  private UserRepository userRepository;

  @Override
  public String create(UserDTO userDTO) {

    User user = UserMapper.INSTANCE.toUser(userDTO);
    validateDataIntegrity(user);
    System.out.println(user);
    //		this.userRepository.save()
    return message("user.created");
  }

  private void validateDataIntegrity(User user) {
    validateEmail(user);
  }

  void validateEmail(User user) {
    if (this.userRepository.existsByEmail(user.getEmail())){
      throw new ValidationException(message("user.email-exists"));
    }
  }
  @Override
  public String updateUser(UserDTO userDTO) {
    return message("user.updated", userDTO.getName());
  }

  @Override
  public void deleteUser(Long id) {}

  @Override
  public Optional<User> getUser(Long id) {
    return Optional.empty();
  }

  @Override
  public List<User> getUsers() {
    return List.of();
  }

  @Autowired
  public void setUserRepository(UserRepository userRepository) {
    this.userRepository = userRepository;
  }
}
