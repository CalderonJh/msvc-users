package org.calderon.users.service.impl;

import dev.jhonc.lib.demolib.exception.ValidationException;
import dev.jhonc.lib.demolib.service.MessagesService;
import java.util.List;
import java.util.Optional;
import org.calderon.users.model.User;
import org.calderon.users.model.dto.UserDTO;
import org.calderon.users.model.mapper.UserMapper;
import org.calderon.users.repository.UserRepository;
import org.calderon.users.service.usecases.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
  private UserRepository userRepository;
  private MessagesService messagesService;

  @Override
  public String create(UserDTO userDTO) {
    var msg = this.messagesService.msg("user.created", userDTO.getName(), userDTO.getLastName());
    User user = UserMapper.INSTANCE.toUser(userDTO);
    validateDataIntegrity(user);
    return msg;
  }

  private void validateDataIntegrity(User user) {
    validateEmail(user);
  }

  void validateEmail(User user) {
    if (this.userRepository.existsByEmail(user.getEmail())) {
      throw new ValidationException(this.messagesService.msg("user.email-exists"));
    }
  }

  @Override
  public String updateUser(UserDTO userDTO) {
    return"user.updated";
  }

  @Override
  public void deleteUser(Long id) {
    this.userRepository.deleteById(id);
  }

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

  @Autowired
  public void setMessagesService(MessagesService messagesService) {
    this.messagesService = messagesService;
  }
}
