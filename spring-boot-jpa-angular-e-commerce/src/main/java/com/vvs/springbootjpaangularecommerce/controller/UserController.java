package com.vvs.springbootjpaangularecommerce.controller;

import java.util.List;

import com.vvs.springbootjpaangularecommerce.model.User;
import com.vvs.springbootjpaangularecommerce.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/users")
public class UserController {
  
  @Autowired
  private UserRepository userRepository;

  @GetMapping("/get")
  public List<User> getUser() {
    return userRepository.findAll();
  }

  @PostMapping("/add")
  public ResponseEntity<?> createUser(@RequestBody User user) {
    return ResponseEntity.ok(userRepository.save(user));
  }

  @DeleteMapping("/{id}")
  public User deleteUser(@PathVariable("id") Long id) {
    User user = userRepository.getOne(id);
    userRepository.deleteById(id);
    return user;
  }
}
