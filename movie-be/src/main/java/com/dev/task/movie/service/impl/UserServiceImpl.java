package com.dev.task.movie.service.impl;


import com.dev.task.movie.exception.AppException;
import com.dev.task.movie.model.dto.CredentialsDTO;
import com.dev.task.movie.model.dto.SignUpDTO;
import com.dev.task.movie.model.dto.UserDTO;
import com.dev.task.movie.model.entity.Role;
import com.dev.task.movie.model.entity.User;
import com.dev.task.movie.model.mapper.UserMapper;
import com.dev.task.movie.repository.RoleRepository;
import com.dev.task.movie.repository.UserRepository;
import com.dev.task.movie.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.Collections;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final RoleRepository roleRepository;

  private final PasswordEncoder passwordEncoder;

  private final UserMapper userMapper;

  public UserDTO login(CredentialsDTO credentialsDto) {
    User user = userRepository.findByUsername(credentialsDto.getUsername())
        .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));

    if (passwordEncoder.matches(CharBuffer.wrap(credentialsDto.getPassword()), user.getPassword())) {
      return userMapper.toUserDto(user);
    }
    throw new AppException("Invalid password", HttpStatus.BAD_REQUEST);
  }

  public UserDTO register(SignUpDTO signUpDTO) {
    Optional<User> optionalUser = userRepository.findByUsername(signUpDTO.getUsername());

    if (optionalUser.isPresent()) {
      throw new AppException("Username already exists", HttpStatus.BAD_REQUEST);
    }

    User user = userMapper.signUpToUser(signUpDTO);

    user.setPassword(passwordEncoder.encode(CharBuffer.wrap(signUpDTO.getPassword())));

    Role userRole = roleRepository.findByName("ROLE_USER");
    if (userRole == null) {
      userRole = roleRepository.save(new Role("ROLE_USER"));
    }

    user.setRoles(Collections.singleton(userRole));

    User savedUser = userRepository.save(user);

    return userMapper.toUserDto(savedUser);
  }

  public UserDTO findByUsername(String username) {
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
    return userMapper.toUserDto(user);
  }

}