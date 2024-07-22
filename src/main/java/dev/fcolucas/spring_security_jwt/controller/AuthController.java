package dev.fcolucas.spring_security_jwt.controller;

import dev.fcolucas.spring_security_jwt.dto.Login;
import dev.fcolucas.spring_security_jwt.dto.Session;
import dev.fcolucas.spring_security_jwt.model.User;
import dev.fcolucas.spring_security_jwt.repository.UserRepository;
import dev.fcolucas.spring_security_jwt.security.JWTCreator;
import dev.fcolucas.spring_security_jwt.security.JWTObject;
import dev.fcolucas.spring_security_jwt.security.SecurityConfig;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private SecurityConfig securityConfig;

  @PostMapping("/login")
  public Session login(@RequestBody Login login) {
    User user = userRepository.findByUsername(login.getUsername());

    if (user == null) {
      throw new RuntimeException("User not found");
    }

    boolean checkPassword = passwordEncoder.matches(login.getPassword(), user.getPassword());
    if (!checkPassword) {
      throw new RuntimeException("Invalid password");
    }

    Session session = new Session();
    session.setUsername(login.getUsername());

    JWTObject jwtObject = new JWTObject();
    jwtObject.setIssuedAt(new Date(System.currentTimeMillis()));
    jwtObject.setExpiration((new Date(System.currentTimeMillis() + SecurityConfig.EXPIRATION)));
    jwtObject.setRoles(user.getRoles());
    session.setToken(JWTCreator.create(SecurityConfig.PREFIX, SecurityConfig.KEY, jwtObject));

    return session;
  }

}
