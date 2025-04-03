package com.dev.task.movie.model.mapper;

import com.dev.task.movie.model.dto.SignUpDTO;
import com.dev.task.movie.model.dto.UserDTO;
import com.dev.task.movie.model.entity.User;

public interface UserMapper {
  UserDTO toUserDto(User user);
  User signUpToUser(SignUpDTO signUpDto);
}
