package com.dev.task.movie.rest;


import com.dev.task.movie.config.UserAuthenticationProvider;
import com.dev.task.movie.model.dto.CredentialsDTO;
import com.dev.task.movie.model.dto.SignUpDTO;
import com.dev.task.movie.model.dto.UserDTO;
import com.dev.task.movie.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RequiredArgsConstructor
@RestController
public class AuthController {

  private final UserService userService;
  private final UserAuthenticationProvider userAuthenticationProvider;

  @PostMapping("/login")
  public ResponseEntity<UserDTO> login(@RequestBody @Valid CredentialsDTO credentialsDto) {
    UserDTO userDto = userService.login(credentialsDto);
    userDto.setToken(userAuthenticationProvider.createToken(userDto));
    return ResponseEntity.ok(userDto);
  }

  @PostMapping("/register")
  public ResponseEntity<UserDTO> register(@RequestBody @Valid SignUpDTO user) {
    UserDTO createdUser = userService.register(user);
    createdUser.setToken(userAuthenticationProvider.createToken(createdUser));
    return ResponseEntity.created(URI.create("/users/" + createdUser.getId())).body(createdUser);
  }

}
