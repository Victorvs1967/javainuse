package com.vvs.springbootjpaangularecommerce.repository;

import com.vvs.springbootjpaangularecommerce.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  
}
