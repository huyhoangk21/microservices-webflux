package io.huyhoang.commentservice.controller;

import io.huyhoang.commentservice.entity.Comment;
import io.huyhoang.commentservice.repository.CommentRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping(value = "/api/comments")
public class CommentController {

    private final CommentRepository commentRepository;

    public CommentController(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @GetMapping
    public Flux<Comment> getAllComments() {
        return commentRepository.findAll();
    }
}
