package io.huyhoang.commentservice.service;

import io.huyhoang.commentservice.dto.CommentDTO;
import io.huyhoang.commentservice.entity.Comment;
import io.huyhoang.commentservice.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Transactional
    public Mono<Comment> addComment(CommentDTO commentDTO, UUID postId) {

        Comment comment = new Comment(UUID.randomUUID(), postId, commentDTO.getContent());

        return commentRepository.save(comment);

    }
}
