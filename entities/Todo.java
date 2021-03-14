package com.tactfactory.javaniveau2.tps.tp8.entities;

public class Todo implements IdItem {
  private Integer userId;
  private Integer id;
  private String title;
  private Boolean completed;

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Boolean getCompleted() {
    return completed;
  }

  public void setCompleted(Boolean completed) {
    this.completed = completed;
  }

  @Override
  public String toString() {
    return "Todo [userId=" + userId + ", id=" + id + ", title=" + title + ", completed=" + completed + "]";
  }

}