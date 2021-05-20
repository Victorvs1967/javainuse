package com.vvs.springbootangular.dao;

import com.vvs.springbootangular.model.DAOUser;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends CrudRepository<DAOUser, Long> {
  DAOUser findByUsername(String username);
}
