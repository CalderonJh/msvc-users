package org.calderon.users.service.impl;

import static dev.jhonc.lib.common.utils.CommonValidations.isValidFieldToUpdate;
import static org.calderon.users.util.MessagesKeys.*;

import dev.jhonc.lib.common.exception.NoDataException;
import dev.jhonc.lib.common.exception.ValidationException;
import dev.jhonc.lib.common.service.Messages;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.calderon.users.model.Address;
import org.calderon.users.model.User;
import org.calderon.users.model.dto.address.AddressDTO;
import org.calderon.users.model.dto.address.AddressPutDTO;
import org.calderon.users.model.dto.user.UserDTO;
import org.calderon.users.model.dto.user.UserPutDTO;
import org.calderon.users.model.mapper.AddressMapper;
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
  private final UserRepository userRepository;

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
  @Transactional
  public User updateUser(UserPutDTO dto) {
    User user =
        repository
            .findById(dto.getId())
            .orElseThrow(() -> (new NoDataException(messages.get(USER_NOT_FOUND, dto.getId()))));
    updateValues(user, dto);
    return repository.save(user);
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
  @Transactional
  public boolean deleteUser(Long id) {
    User user =
        repository
            .findById(id)
            .orElseThrow(() -> (new NoDataException(messages.get(USER_NOT_FOUND, id))));
    repository.delete(user);
    return false;
  }

  @Override
  @Transactional
  public List<Address> addAddress(Long idUser, AddressDTO addressDTO) {
    Address address = AddressMapper.INSTANCE.toAddress(addressDTO);
    User user = getUser(idUser);
    user.addAddress(address);
    return userRepository.save(user).getAddresses();
  }

  @Override
  @Transactional
  public Address updateAddress(AddressPutDTO addressDTO) {
    Address address =
        addressRepository
            .findById(addressDTO.getId())
            .orElseThrow(
                () -> (new NoDataException(messages.get(ADDRESS_NOT_FOUND, addressDTO.getId()))));
    updateAddressValues(address, addressDTO);
    if (addressDTO.isRemoveDescription()) address.setDescription(null);
    return addressRepository.save(address);
  }

  void updateAddressValues(Address addressDB, AddressPutDTO addressDTO) {
    if (isValidFieldToUpdate(addressDTO.getStreet())) addressDB.setStreet(addressDTO.getStreet());
    if (isValidFieldToUpdate(addressDTO.getNumber())) addressDB.setNumber(addressDTO.getNumber());
    if (isValidFieldToUpdate(addressDTO.getCity())) addressDB.setCity(addressDTO.getCity());
    if (isValidFieldToUpdate(addressDTO.getState())) addressDB.setState(addressDTO.getState());
    if (isValidFieldToUpdate(addressDTO.getCountry()))
      addressDB.setCountry(addressDTO.getCountry());
    if (isValidFieldToUpdate(addressDTO.getDescription()))
      addressDB.setDescription(addressDTO.getDescription());
  }

  @Override
  @Transactional
  public boolean deleteAddress(Long id) {
    Address address =
        addressRepository
            .findById(id)
            .orElseThrow(() -> (new NoDataException(messages.get(ADDRESS_NOT_FOUND, id))));
    addressRepository.delete(address);
    return true;
  }
}
