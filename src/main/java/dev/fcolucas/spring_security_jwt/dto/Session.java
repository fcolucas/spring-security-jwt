package dev.fcolucas.spring_security_jwt.dto;

public class Session {
  private String username;
  private String token;

  public String getToken() {
    return token;
  }

  public String getUsername() {
    return username;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public void setUsername(String username) {
    this.username = username;
  }
}
