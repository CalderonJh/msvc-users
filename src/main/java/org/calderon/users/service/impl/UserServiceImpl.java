package org.calderon.users.service.impl;


import dev.jhonc.lib.common.exception.NoDataException;
import dev.jhonc.lib.common.exception.ValidationException;
import dev.jhonc.lib.common.service.Messages;
import lombok.RequiredArgsConstructor;
import org.calderon.users.model.User;
import org.calderon.users.model.dto.user.UserDTO;
import org.calderon.users.model.mapper.UserMapper;
import org.calderon.users.repository.UserRepository;
import org.calderon.users.service.usecases.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepository repository;
  private final Messages messages;

  @Override
  public String create(UserDTO userDTO) {
    User user = UserMapper.INSTANCE.toUser(userDTO);
    validateDataIntegrity(user);
    this.repository.save(user);
    return this.messages.show("user.created", userDTO.getName(), userDTO.getLastName());
  }

  private void validateDataIntegrity(User user) {
    validateEmail(user);
  }

  void validateEmail(User user) {
    if (this.repository.existsByEmail(user.getEmail())) {
      throw new ValidationException(this.messages.show("user.email-exists"));
    }
  }

  @Override
  public String updateUser(UserDTO userDTO) {
    return "user.updated";
  }

  @Override
  public void deleteUser(Long id) {
    this.repository.deleteById(id);
  }

  @Override
  public User getUser(Long id) {
    return this.repository
        .findById(id)
        .orElseThrow(() -> (new NoDataException(this.messages.show("user.not-found", id))));
  }

  @Override
  public Page<User> getUsers(Pageable pageable) {
    return this.repository.findAll(pageable);
  }
}
