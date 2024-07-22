package dev.fcolucas.spring_security_jwt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import dev.fcolucas.spring_security_jwt.model.User;
import dev.fcolucas.spring_security_jwt.repository.UserRepository;

@Service
public class UserService {
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder encoder;

  public void createUser(User user) {
    String pass = user.getPassword();
    user.setPassword(encoder.encode(pass));
    userRepository.save(user);
  }
}
