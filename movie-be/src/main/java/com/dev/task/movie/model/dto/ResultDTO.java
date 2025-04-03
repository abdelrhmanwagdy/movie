package com.dev.task.movie.model.dto;
public class ResultDTO<T> {
  private T result;
  private ErrorDTO error;
  private String message;

  public T getResult() { return result; }
  public void setResult(T result) { this.result = result; }
  public ErrorDTO getError() {
    return error;
  }
  public void setError(ErrorDTO error) {
    this.error = error;
  }
  public String getMessage() { return message; }
  public void setMessage(String message) { this.message = message; }
}
