package com.vvs.springbootjpaangularecommerce.controller;

import java.io.IOException;
import java.util.List;

import com.vvs.springbootjpaangularecommerce.model.Book;
import com.vvs.springbootjpaangularecommerce.repository.BookRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/books")
public class BookController {

  private byte[] bytes;

  @Autowired
  private BookRepository bookRepository;
  
  @GetMapping("/get")
  public List<Book> getBooks() {
    return bookRepository.findAll();
  }

  @PostMapping("/upload")
  public void uploadImage(@RequestParam("imageFile") MultipartFile file) throws IOException {
    this.bytes = file.getBytes();
  }

  @PostMapping("/add")
  public void createBook(@RequestBody Book book) throws IOException {
    book.setPicByte(this.bytes);
    bookRepository.save(book);
    this.bytes = null;
  }

  @DeleteMapping("/{id}")
  public Book deleteBook(@PathVariable("id") Long id) {
    Book book = this.bookRepository.getOne(id);
    bookRepository.deleteById(id);
    return book;
  }

  @PutMapping("/update")
  public void updateBook(@RequestBody Book book) {
    bookRepository.save(book);
  }

}
