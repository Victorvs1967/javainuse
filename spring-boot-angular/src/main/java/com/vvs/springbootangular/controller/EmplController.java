package com.vvs.springbootangular.controller;

import java.util.ArrayList;
import java.util.List;

import com.vvs.springbootangular.model.Employee;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class EmplController {
  
  private List<Employee> employees = createList();

  @GetMapping("/employees")
  public List<Employee> firstPage() {
    return employees;
  }

  private static List<Employee> createList() {
    List<Employee> tempEmployees = new ArrayList<>();

    Employee emp1 = new Employee();
    emp1.setName("emp1");
    emp1.setDesignation("manager");
    emp1.setId("1");
    emp1.setSalary(1000);

    Employee emp2 = new Employee();
    emp2.setName("emp2");
    emp2.setDesignation("developer");
    emp2.setId("2");
    emp2.setSalary(2000);

    tempEmployees.add(emp1);
    tempEmployees.add(emp2);

    return tempEmployees;
  }
}
