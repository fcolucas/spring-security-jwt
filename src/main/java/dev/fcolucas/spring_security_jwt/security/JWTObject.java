package dev.fcolucas.spring_security_jwt.security;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class JWTObject {
  private String subject; // nome do usuario
  private Date issuedAt; // data de criação do token
  private Date expiration; // data de expiração do token
  private List<String> roles; // perfis de acesso

  public String getSubject() {
    return subject;
  }

  public Date getIssuedAt() {
    return issuedAt;
  }

  public Date getExpiration() {
    return expiration;
  }

  public List<String> getRoles() {
    return roles;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public void setIssuedAt(Date issuedAt) {
    this.issuedAt = issuedAt;
  }

  public void setExpiration(Date expiration) {
    this.expiration = expiration;
  }

  public void setRoles(List<String> roles) {
    this.roles = roles;
  }
}
