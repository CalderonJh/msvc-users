package org.calderon.users.service.impl;

import static org.calderon.users.util.CommonValidations.isValidFieldToUpdate;
import static org.calderon.users.util.MessagesKeys.*;

import dev.jhonc.lib.common.exception.NoDataException;
import dev.jhonc.lib.common.exception.ValidationException;
import dev.jhonc.lib.common.service.Messages;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.calderon.users.model.Address;
import org.calderon.users.model.User;
import org.calderon.users.model.dto.address.AddressPutDTO;
import org.calderon.users.model.dto.user.UserDTO;
import org.calderon.users.model.dto.user.UserPutDTO;
import org.calderon.users.model.mapper.UserMapper;
import org.calderon.users.repository.AddressRepository;
import org.calderon.users.repository.UserRepository;
import org.calderon.users.service.usecases.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepository repository;
  private final AddressRepository addressRepository;
  private final Messages messages;

  @Override
  @Transactional
  public User create(UserDTO userDTO) {
    User user = UserMapper.INSTANCE.toUser(userDTO);
    validateEmail(user.getEmail());
    return repository.save(user);
  }

  void validateEmail(String email) {
    if (repository.existsByEmail(email)) {
      throw new ValidationException(messages.get(USER_EMAIL_EXISTS));
    }
  }

  @Override
  @Transactional
  public User updateUser(UserPutDTO dto) {
    User user =
        repository
            .findById(dto.getId())
            .orElseThrow(() -> (new NoDataException(messages.get(USER_NOT_FOUND, dto.getId()))));
    updateValues(user, dto);
    return new User();
  }

  void updateValues(User userDB, UserPutDTO userDTO) {
    if (isValidFieldToUpdate(userDTO.getName())) userDB.setName(userDTO.getName());
    if (isValidFieldToUpdate(userDTO.getLastName())) userDB.setLastName(userDTO.getLastName());
    if (isValidFieldToUpdate(userDTO.getEmail()) && !userDB.getEmail().equals(userDTO.getEmail())) {
      validateEmail(userDTO.getEmail());
      userDB.setEmail(userDTO.getEmail());
    }
  }

  @Override
  public Address updateAddress(AddressPutDTO addressDTO) {
    Address address =
        addressRepository
            .findById(addressDTO.getId())
            .orElseThrow(
                () -> (new NoDataException(messages.get(ADDRESS_NOT_FOUND, addressDTO.getId()))));
    updateAddressValues(address, addressDTO);
    return addressRepository.save(address);
  }

  void updateAddressValues(Address addressDB, AddressPutDTO addressDTO) {
    if (isValidFieldToUpdate(addressDTO.getStreet())) addressDB.setStreet(addressDTO.getStreet());
    if (isValidFieldToUpdate(addressDTO.getNumber())) addressDB.setNumber(addressDTO.getNumber());
    if (isValidFieldToUpdate(addressDTO.getCity())) addressDB.setCity(addressDTO.getCity());
    if (isValidFieldToUpdate(addressDTO.getState())) addressDB.setState(addressDTO.getState());
    if (isValidFieldToUpdate(addressDTO.getCountry()))
      addressDB.setCountry(addressDTO.getCountry());
    if (addressDTO.getDescription() != null) addressDB.setDescription(addressDTO.getDescription());
  }

  @Override
  public boolean deleteUser(Long id) {
    User user =
        repository
            .findById(id)
            .orElseThrow(() -> (new NoDataException(messages.get(USER_NOT_FOUND, id))));
    repository.delete(user);
    return false;
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

  @Override
  public Object test() {
    User user =
        User.builder()
            .id(1L)
            .name("Name")
            .lastName("Last Name")
            .email("email@test.com")
            .addresses(
                List.of(
                    Address.builder()
                        .street("street")
                        .state("state")
                        .city("city")
                        .number(1)
                        .country("country")
                        .build()))
            .build();
    return user.getClass().getFields();
  }
}
