package com.vvs.springbootjpaangularecommerce.repository;

import com.vvs.springbootjpaangularecommerce.model.Book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
  
}
