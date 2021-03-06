package com.vvs.springbootangular.controller;

import java.util.Objects;

import com.vvs.springbootangular.dao.UserDao;
import com.vvs.springbootangular.jwt.JwtTokenUtil;
import com.vvs.springbootangular.model.JwtRequest;
import com.vvs.springbootangular.model.JwtResponse;
import com.vvs.springbootangular.model.UserDTO;
import com.vvs.springbootangular.service.JwtUserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class JwtAuthenticationController {
  
  @Autowired
  private UserDao userDao;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  @Autowired
  private JwtUserDetailsService userDetailsService;

  @PostMapping("/authenticate")
  public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

    String username = authenticationRequest.getUsername();
    String password = authenticationRequest.getPassword();
    authenticate(username, password);
    final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
    final String token = jwtTokenUtil.generateToken(userDetails);

    return ResponseEntity.ok(new JwtResponse(token));
  }

  @PostMapping("/register")
  public ResponseEntity<?> saveUser(@RequestBody UserDTO user) throws Exception {
    try {
      if (this.userDao.findByUsername(user.getUsername()) == null) {
        return ResponseEntity.ok(userDetailsService.save(user));
      } else {
        throw new Exception("Username already exist: " + user.getUsername());
      }
    } catch (Exception e) {
      throw new Exception("Invalide registration", e);
    }
  }

  private void authenticate(String username, String password) throws DisabledException, BadCredentialsException {
    Objects.requireNonNull(username);
    Objects.requireNonNull(password);

    try {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    } catch (DisabledException e) {
      throw new DisabledException("USER_DISABLE", e);
    } catch (BadCredentialsException e) {
      throw new BadCredentialsException("INVALID_CREDENTIALS", e);
    }
  }
}
