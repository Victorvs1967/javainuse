package com.vvs.springbootangular.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
  
  private String id;
  private String name;
  private String designation;
  private double salary;

}
