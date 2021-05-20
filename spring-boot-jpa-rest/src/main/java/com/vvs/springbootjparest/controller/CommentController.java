package com.vvs.springbootjparest.controller;

import java.util.List;

import com.vvs.springbootjparest.exception.ResourceNotFoundException;
import com.vvs.springbootjparest.model.Comment;
import com.vvs.springbootjparest.repository.CommentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CommentController {
  
  @Autowired
  CommentRepository commentRepository;

  @GetMapping("/comments")
  public List<Comment> getAllComments() {
    return commentRepository.findAll();
  }

  @PostMapping("/comments")
  public Comment createComment(@RequestBody Comment comment) {
    return commentRepository.save(comment);
  }

  @GetMapping("/comments/{id}")
  public Comment getCommentById(@PathVariable("id") Long commentId) {
    return commentRepository.findById(commentId)
            .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
  }

  @PutMapping("/comments/{id}")
  public Comment updateComment(@PathVariable("id") Long commentId, @RequestBody Comment commentDetails) {
    Comment comment = commentRepository.findById(commentId)
                      .orElseThrow(() -> new ResourceNotFoundException("comment", "id", commentId));
    comment.setPostName(commentDetails.getPostName());
    comment.setCommentBody(commentDetails.getCommentBody());
    return commentRepository.save(comment);
  }

  @DeleteMapping("/comments/{id}")
  public ResponseEntity<?> deleteComment(@PathVariable("id") Long commentId) {
    Comment comment = commentRepository.findById(commentId)
                      .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
    commentRepository.delete(comment);
    return ResponseEntity.ok().build();
  }
}
