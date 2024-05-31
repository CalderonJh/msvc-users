package org.calderon.users.controller;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.calderon.users.model.dto.address.AddressDTO;
import org.calderon.users.model.dto.address.AddressPutDTO;
import org.calderon.users.model.dto.user.UserDTO;
import org.calderon.users.model.dto.user.UserPutDTO;
import org.calderon.users.model.mapper.AddressMapper;
import org.calderon.users.model.mapper.UserMapper;
import org.calderon.users.service.usecases.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
  private final UserService service;

  @PostMapping("/signup")
  public ResponseEntity<UserDTO> signup(@RequestBody @Valid UserDTO userDTO) {
    return ResponseEntity.ok(UserMapper.INSTANCE.toUserDTO(service.create(userDTO)));
  }

  @GetMapping("/all")
  public ResponseEntity<Page<UserDTO>> getUsers(@PageableDefault Pageable pageable) {
    Page<UserDTO> page = service.getUsers(pageable).map(UserMapper.INSTANCE::toUserDTO);
    return ResponseEntity.ok(page);
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
    return ResponseEntity.ok(UserMapper.INSTANCE.toUserDTO(service.getUser(id)));
  }

  @PutMapping("/update")
  public ResponseEntity<UserDTO> updateUser(@RequestBody UserPutDTO dto) {
    return ResponseEntity.ok(UserMapper.INSTANCE.toUserDTO(service.updateUser(dto)));
  }

  @DeleteMapping("/remove/{id}")
  public ResponseEntity<Boolean> deleteUser(@PathVariable Long id) {
    return ResponseEntity.ok(service.deleteUser(id));
  }

  @PostMapping("{idUser}/create-address")
  public ResponseEntity<List<AddressDTO>> createAddress(
      @PathVariable Long idUser, @RequestBody @Valid AddressDTO addressDTO) {
    return ResponseEntity.ok(
        this.service.addAddress(idUser, addressDTO).stream()
            .map(AddressMapper.INSTANCE::toAddressDTO)
            .toList());
  }

  @PutMapping("/update-address")
  public ResponseEntity<AddressDTO> updateAddress(@RequestBody @Valid AddressPutDTO addressDTO) {
    return ResponseEntity.ok(
        AddressMapper.INSTANCE.toAddressDTO(this.service.updateAddress(addressDTO)));
  }

  @DeleteMapping("/delete-address/{id}")
  public ResponseEntity<Boolean> deleteAddress(@PathVariable Long id) {
    return ResponseEntity.ok(service.deleteAddress(id));
  }
}
