package dev.fcolucas.spring_security_jwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.fcolucas.spring_security_jwt.model.User;
import dev.fcolucas.spring_security_jwt.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/users")
public class UserController {
  @Autowired
  private UserService userService;

  @PostMapping
  public void createUser(@RequestBody User user) {
    userService.createUser(user);
  }

}
