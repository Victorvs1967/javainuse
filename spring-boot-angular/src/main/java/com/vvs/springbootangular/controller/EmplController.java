package com.vvs.springbootangular.controller;

import java.util.ArrayList;
import java.util.List;

import com.vvs.springbootangular.model.Employee;
import com.vvs.springbootangular.model.User;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin()
@RestController
@RequestMapping("/employees")
public class EmplController {
  
  private List<Employee> employees = createList();

  @GetMapping
  public List<Employee> firstPage() {
    return employees;
  }

  @GetMapping(produces = "application/json")
  @RequestMapping({"/validateLogin"})
  public User validateLogin() {
    return new User("User successfully authenticated");
  }

  @DeleteMapping("/{id}")
  public Employee delete(@PathVariable("id") String id) {
    Employee deletedEmp = null;
    for (Employee emp : employees) {
      if (emp.getId().equals(id)) {
        employees.remove(emp);
        deletedEmp = emp;
        break;
      }
    }
    return deletedEmp;
  }

  @PostMapping
  public Employee create(@RequestBody Employee emp) {
    employees.add(emp);
    return emp;
  }

  private static List<Employee> createList() {
    List<Employee> tempEmployees = new ArrayList<>();

    Employee emp1 = new Employee("1", "emp1", "manager", 1000);
    Employee emp2 = new Employee("2", "emp2", "developer", 2000);

    tempEmployees.add(emp1);
    tempEmployees.add(emp2);

    return tempEmployees;
  }

}
