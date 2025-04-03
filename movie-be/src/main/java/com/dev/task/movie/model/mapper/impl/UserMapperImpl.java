package com.dev.task.movie.model.mapper.impl;

import com.dev.task.movie.model.dto.SignUpDTO;
import com.dev.task.movie.model.dto.UserDTO;
import com.dev.task.movie.model.entity.User;
import com.dev.task.movie.model.entity.Role;
import com.dev.task.movie.model.mapper.UserMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapperImpl implements UserMapper {

  @Override
  public UserDTO toUserDto(User user) {
    UserDTO userDTO = new UserDTO();
    userDTO.setId(user.getId());
    userDTO.setFirstName(user.getFirstName());
    userDTO.setLastName(user.getLastName());
    userDTO.setUsername(user.getUsername());

    List<String> rolesList = user.getRoles().stream()
        .map(Role::getName) // Assuming Role has a method getName() returning a String
        .collect(Collectors.toList());

    // Now set the rolesList to userDTO
    userDTO.setRoles(rolesList);

    return userDTO;
  }

  @Override
  public User signUpToUser(SignUpDTO signUpDto) {
    User user = new User();
    user.setFirstName(signUpDto.getFirstName());
    user.setLastName(signUpDto.getLastName());
    user.setUsername(signUpDto.getUsername());
    user.setPassword(signUpDto.getPassword());
    return user;
  }
}
