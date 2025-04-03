package com.dev.task.movie.rest;

import com.dev.task.movie.model.dto.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalRestExceptionHandler {
  @ExceptionHandler
  public ResponseEntity<ErrorDTO> handleException(Exception exc) {

    ErrorDTO error = new ErrorDTO();
    error.setError(exc.getMessage());

    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }
}
