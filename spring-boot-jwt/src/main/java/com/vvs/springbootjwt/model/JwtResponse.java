package com.vvs.springbootjwt.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class JwtResponse implements Serializable {
  
  private final String jwttoken;
  
}
