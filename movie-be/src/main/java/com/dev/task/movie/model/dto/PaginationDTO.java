package com.dev.task.movie.model.dto;


public class PaginationDTO {
  public int page;
  public int size;

  public int getPage() {
    return page;
  }

  public void setPage(int page) {
    this.page = page;
  }

  public int getSize() {
    return size;
  }

  public void setSize(int size) {
    this.size = size;
  }
}
