package com.github.r21.ecommerce;

public class User {

  private final String uuid;

  public User(String uuid) {
    this.uuid = uuid;
  }

  public String getReportPath() {
    return "target/" + uuid + "-report.txt";
  }

  public String getUuid() {
    return this.uuid;
  }
}
