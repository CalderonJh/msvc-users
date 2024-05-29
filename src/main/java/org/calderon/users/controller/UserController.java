package org.calderon.users.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.calderon.users.model.dto.user.UserDTO;
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

  @GetMapping("/test")
  public ResponseEntity<String> test() {
    return ResponseEntity.ok(
        service.create(
            UserDTO.builder()
                .name("name t")
                .lastName("last name t")
                .email("test@email.com")
                .build()));
  }

  @PostMapping("/signup")
  public ResponseEntity<String> signup(@RequestBody @Valid UserDTO userDTO) {
    return ResponseEntity.ok(service.create(userDTO));
  }

  @GetMapping("/all")
  public ResponseEntity<Page<UserDTO>> getUsers(@PageableDefault Pageable pageable) {
    return ResponseEntity.ok(service.getUsers(pageable).map(UserMapper.INSTANCE::toUserDTO));
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
    return ResponseEntity.ok(UserMapper.INSTANCE.toUserDTO(service.getUser(id)));
  }
}
