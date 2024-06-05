package org.calderon.users.service.impl;

import static org.calderon.users.util.MessagesKeys.USER_EMAIL_EXISTS;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import dev.jhonc.lib.common.exception.ValidationException;
import dev.jhonc.lib.common.service.Messages;

import java.util.Optional;
import org.calderon.users.model.entity.Address;
import org.calderon.users.model.entity.User;
import org.calderon.users.model.dto.address.AddressDTO;
import org.calderon.users.model.dto.address.AddressPutDTO;
import org.calderon.users.model.dto.user.UserDTO;
import org.calderon.users.model.dto.user.UserPutDTO;
import org.calderon.users.model.mapper.UserMapper;
import org.calderon.users.repository.AddressRepository;
import org.calderon.users.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class UserServiceImplTest {
  @InjectMocks private UserServiceImpl service;
  @Mock private Messages messages;
  @Mock private UserRepository userRepository;
  @Mock private AddressRepository addressRepository;

  private User user;

  @BeforeEach
  void setUp() {
    this.user =
        User.builder().id(1L).name("John").lastName("Doe").email("email1@email.com").build();
  }

  @Test
  void createUser() {
    UserDTO userDTO = UserMapper.INSTANCE.toUserDTO(user);
    when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArguments()[0]);
    User createdUser = service.create(userDTO);
    assertEquals(userDTO.getName(), createdUser.getName());
    assertEquals(userDTO.getLastName(), createdUser.getLastName());
    assertEquals(userDTO.getEmail(), createdUser.getEmail());
  }

  @Test
  void createUserWhenEmailExists() {
    UserDTO userDTO =
        UserDTO.builder()
            .name("test name")
            .lastName("test last name")
            .email("emailthatexists@email.com")
            .build();

    when(userRepository.existsByEmail("emailthatexists@email.com")).thenReturn(true);
    when(messages.get(any(String.class))).thenAnswer(i -> i.getArguments()[0]);
    Exception e = assertThrows(ValidationException.class, () -> service.create(userDTO));
    assertEquals(USER_EMAIL_EXISTS, e.getMessage());
  }

  @Test
  void updateUserEmail() {
    UserPutDTO userDTO = UserPutDTO.builder().id(1L).email("otheremail@email.com").build();

    when(userRepository.existsByEmail("otheremail@email.com")).thenReturn(false);
    when(userRepository.findById(1L)).thenReturn(Optional.of(user));
    when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArguments()[0]);

    User createdUser = service.updateUser(userDTO);
    assertNotNull(createdUser.getName());
    assertNotNull(createdUser.getLastName());
    assertEquals(userDTO.getEmail(), createdUser.getEmail());
  }

  @Test
  void updateUserWithTheSameEmail() {
    UserPutDTO userDTO = UserPutDTO.builder().id(1L).email(user.getEmail()).build();

    when(userRepository.findById(1L)).thenReturn(Optional.of(user));
    when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArguments()[0]);
    assertDoesNotThrow(() -> service.updateUser(userDTO));
  }

  @Test
  void updateUserWhenNoNullableFieldIsNull() {
    UserPutDTO userDTO =
        UserPutDTO.builder()
            .id(1L)
            .name("test name")
            .lastName("test last name")
            .email(null)
            .build();
    when(userRepository.findById(1L)).thenReturn(Optional.of(user));
    when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArguments()[0]);
    User updatedUser = service.updateUser(userDTO);
    assertEquals("test name", updatedUser.getName());
    assertEquals("test last name", updatedUser.getLastName());
    assertNotNull(updatedUser.getEmail());
  }

  @Test
  void addAddress() {
    user
        .getAddresses()
        .add(
            Address.builder()
                .id(1L)
                .number(1)
                .street("street")
                .city("city")
                .state("state")
                .country("country")
                .build());
    AddressDTO addressToAdd =
        AddressDTO.builder()
            .number(2)
            .street("new street")
            .city("new city")
            .state("new state")
            .country("new country")
            .build();
    when(userRepository.findById(1L)).thenReturn(Optional.of(user));
    when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArguments()[0]);
    this.service.addAddress(1L, addressToAdd);
    assertEquals(2, user.getAddresses().size());
    assertEquals(2, user.getAddresses().get(1).getNumber());
    assertEquals("new street", user.getAddresses().get(1).getStreet());
    assertEquals("new city", user.getAddresses().get(1).getCity());
    assertEquals("new state", user.getAddresses().get(1).getState());
    assertEquals("new country", user.getAddresses().get(1).getCountry());
  }

  @Test
  void updateAddress() {
    addAddressToUser();

    AddressPutDTO addressPutDTO =
        AddressPutDTO.builder()
            .id(1L)
            .number(3)
            .street("new street")
            .city("new city")
            .build();

    when(addressRepository.findById(1L)).thenReturn(Optional.of(user.getAddresses().get(0)));
    when(addressRepository.save(any(Address.class))).thenAnswer(i -> i.getArguments()[0]);

    Address updatedAddress = service.updateAddress(addressPutDTO);
    assertEquals(3, updatedAddress.getNumber());
    assertEquals("new street", updatedAddress.getStreet());
    assertEquals("new city", updatedAddress.getCity());
    assertEquals("state1", updatedAddress.getState());
    assertEquals("country1", updatedAddress.getCountry());
    assertNotNull(updatedAddress.getDescription());
  }

  @Test
  void removeAddressDescription() {
    addAddressToUser();
    AddressPutDTO addressPutDTO =
        AddressPutDTO.builder().id(1L).removeDescription(true).build();

      when(addressRepository.findById(1L)).thenReturn(Optional.of(user.getAddresses().get(0)));
      when(addressRepository.save(any(Address.class))).thenAnswer(i -> i.getArguments()[0]);

      Address updatedAddress = service.updateAddress(addressPutDTO);
      assertNull(updatedAddress.getDescription());
  }

  void addAddressToUser() {
    user
        .getAddresses()
        .add(
            Address.builder()
                .id(1L)
                .number(1)
                .street("street1")
                .city("city1")
                .state("state1")
                .country("country1")
                .description("description1")
                .build());
  }
}