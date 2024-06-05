package org.calderon.users.service.usecases;

import org.calderon.users.model.entity.Address;
import org.calderon.users.model.entity.User;
import org.calderon.users.model.dto.address.AddressDTO;
import org.calderon.users.model.dto.address.AddressPutDTO;
import org.calderon.users.model.dto.user.UserDTO;
import org.calderon.users.model.dto.user.UserPutDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
  User create(UserDTO userDTO);

  User updateUser(UserPutDTO userPutDTO);

  Address updateAddress(AddressPutDTO addressDTO);

  List<Address> addAddress(Long idUser, AddressDTO addressDTO);

  boolean deleteAddress(Long id);

  boolean deleteUser(Long id);

  User getUser(Long id);

  Page<User> getUsers(Pageable pageable);
  User buyCourse(Long idUser, String idCourse);
}