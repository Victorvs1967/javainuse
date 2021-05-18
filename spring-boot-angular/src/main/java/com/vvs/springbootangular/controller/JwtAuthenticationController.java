package com.vvs.springbootangular.controller;

import java.util.Objects;

import com.vvs.springbootangular.jwt.JwtTokenUtil;
import com.vvs.springbootangular.model.JwtRequest;
import com.vvs.springbootangular.model.JwtResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class JwtAuthenticationController {
  
  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  @Autowired
  private UserDetailsService jwtUserDetailsService;

  @PostMapping("/authenticate")
  public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

    String username = authenticationRequest.getUsername();
    String password = authenticationRequest.getPassword();
    authenticate(username, password);
    final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);
    final String token = jwtTokenUtil.generateToken(userDetails);

    return ResponseEntity.ok(new JwtResponse(token));
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
