package io.huyhoang.commentservice.service;

import io.huyhoang.commentservice.dto.CommentRequest;
import io.huyhoang.commentservice.dto.CommentResponse;
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

    @Transactional(readOnly = true)
    public Flux<CommentResponse> allByPost(UUID postId) {
        return commentRepository.findByPostIdOrderByCreatedAtDesc(postId)
                .flatMap(this::convertDTO);
    }

    @Transactional
    public Mono<CommentResponse> add(CommentRequest commentRequest, UUID postId) {
        return commentRepository.save(new Comment(UUID.randomUUID(), postId, commentRequest.getContent()))
                .flatMap(this::convertDTO);
    }

    @Transactional
    public Mono<CommentResponse> edit(CommentRequest commentRequest, UUID commentId) {
        return commentRepository.findById(commentId)
                .switchIfEmpty(Mono.error(RuntimeException::new))
                .flatMap(comment -> {
                    comment.setContent(commentRequest.getContent());
                    return commentRepository.save(comment);
                })
                .flatMap(this::convertDTO);
    }

    @Transactional
    public Mono<Void> delete(UUID commentId) {
        return commentRepository.findById(commentId)
                .switchIfEmpty(Mono.error(RuntimeException::new))
                .flatMap(commentRepository::delete);
    }

    private Mono<CommentResponse> convertDTO(Comment comment) {
        return Mono.just(new CommentResponse(
                comment.getCommentId(),
                comment.getPostId(),
                comment.getUserId(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getUpdatedAt()));
    }
}
