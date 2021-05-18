package com.vvs.springbootjwt.service;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    if ("victor".equals(username)) {
      return new User("victor", "$2a$10$KmQeBGBfALdO0GzoWGcbnOFbl50ak5BAyONPmFDBmL/dS03z8xmlq", new ArrayList<>());
    } else {
      throw new UsernameNotFoundException("User not found whith username: " + username);
    }
  }
  
}
