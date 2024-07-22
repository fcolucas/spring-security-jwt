package dev.fcolucas.spring_security_jwt.dto;

public class Login {
  private String username;
  private String password;

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public void setUsername(String login) {
    this.username = login;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
