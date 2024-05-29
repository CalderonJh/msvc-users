package org.calderon.users.service.impl;

import static org.calderon.users.util.MessagesKeys.*;

import dev.jhonc.lib.common.exception.NoDataException;
import dev.jhonc.lib.common.exception.ValidationException;
import dev.jhonc.lib.common.service.Messages;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.calderon.users.model.Address;
import org.calderon.users.model.User;
import org.calderon.users.model.dto.user.UserDTO;
import org.calderon.users.model.dto.user.UserPatchDTO;
import org.calderon.users.model.mapper.AddressMapper;
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
  @Transactional
  public String create(UserDTO userDTO) {
    User user = UserMapper.INSTANCE.toUser(userDTO);
    validateEmail(user.getEmail());
    repository.save(user);
    return messages.get(USER_CREATED, userDTO.getName(), userDTO.getLastName());
  }

  void validateEmail(String email) {
    if (repository.existsByEmail(email)) {
      throw new ValidationException(messages.get(USER_EMAIL_EXISTS));
    }
  }

  @Override
  @Transactional
  public String updateUser(UserPatchDTO userDTO) {
    User userDB =
        this.repository
            .findById(userDTO.getId())
            .orElseThrow(
                () -> (new NoDataException(messages.get(USER_NOT_FOUND, userDTO.getId()))));
    updateNoNullValues(userDB, userDTO);
    repository.save(userDB);
    return messages.get(USER_UPDATED);
  }

  private void updateNoNullValues(User userDB, UserPatchDTO userDTO) {
    if (userDTO.getName() != null && !userDTO.getName().isBlank())
      userDB.setName(userDTO.getName());
    if (userDTO.getLastName() != null && !userDTO.getLastName().isBlank())
      userDB.setLastName(userDTO.getLastName());
    if (userDTO.getEmail() != null
        && !userDTO.getEmail().isBlank()
        && !userDTO.getEmail().equals(userDB.getEmail())) {
      validateEmail(userDTO.getEmail());
      userDB.setEmail(userDTO.getEmail());
    }
    if (userDTO.getAddresses() != null) {
      List<Address> addresses = new ArrayList<>();
      Arrays.stream(userDTO.getAddresses())
          .forEach(address -> addresses.add(AddressMapper.INSTANCE.toAddress(address)));
      userDB.setAddresses(addresses);
    }
  }

  @Override
  public String deleteUser(Long id) {
    User user =
        repository
            .findById(id)
            .orElseThrow(() -> (new NoDataException(messages.get(USER_NOT_FOUND, id))));
    repository.delete(user);
    return messages.get(USER_DELETED);
  }

  @Override
  public User getUser(Long id) {
    return repository
        .findById(id)
        .orElseThrow(() -> (new NoDataException(messages.get(USER_NOT_FOUND, id))));
  }

  @Override
  public Page<User> getUsers(Pageable pageable) {
    return repository.findAll(pageable);
  }
}
