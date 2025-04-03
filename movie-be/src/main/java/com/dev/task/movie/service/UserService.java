package com.dev.task.movie.service;

import com.dev.task.movie.model.dto.CredentialsDTO;
import com.dev.task.movie.model.dto.SignUpDTO;
import com.dev.task.movie.model.dto.UserDTO;

public interface UserService {
  public UserDTO login(CredentialsDTO credentialsDto);

  public UserDTO register(SignUpDTO signUpDTO);

  public UserDTO findByUsername(String username);
}
