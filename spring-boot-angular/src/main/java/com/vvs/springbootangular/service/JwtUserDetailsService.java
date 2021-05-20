package com.vvs.springbootangular.service;

import java.util.ArrayList;

import com.vvs.springbootangular.dao.UserDao;
import com.vvs.springbootangular.model.DAOUser;
import com.vvs.springbootangular.model.UserDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

  @Autowired
  private UserDao userDao;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    DAOUser user = userDao.findByUsername(username);

    if (user == null) {
      throw new UsernameNotFoundException("User not found whith username: " + username);
    }

    return new User(user.getUsername(), user.getPassword(), new ArrayList<>());

  }

  public DAOUser save(UserDTO user) {
    DAOUser newUser = new DAOUser();
    newUser.setUsername(user.getUsername());
    newUser.setPassword(passwordEncoder.encode(user.getPassword()));
    return userDao.save(newUser);
  }
  
}
