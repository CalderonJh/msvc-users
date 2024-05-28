package org.calderon.users.controller.rest;

import org.calderon.users.model.dto.UserDTO;
import org.calderon.users.service.usecases.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

  private UserService service;

  @GetMapping("/test")
  public ResponseEntity<String> test() {
    return ResponseEntity.ok(
        service.updateUser(
            UserDTO.builder()
                .name("name t")
                .lastName("last name t")
                .email("test@email.com")
                .build()));
  }

  @PostMapping("/signup")
  public ResponseEntity<String> signup(UserDTO userDTO) {
    return ResponseEntity.ok(service.create(userDTO));
  }

  @Autowired
  public void setUserService(UserService service) {
    this.service = service;
  }
}
