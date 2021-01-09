package io.huyhoang.commentservice.controller;

import io.huyhoang.commentservice.dto.CommentRequest;
import io.huyhoang.commentservice.dto.CommentResponse;
import io.huyhoang.commentservice.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }


    @GetMapping(value = "/posts/{postId}/comments")
    public Flux<CommentResponse> allCommentsByPost(@PathVariable("postId") UUID postId) {
        return commentService.allByPost(postId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/posts/{postId}/comments")
    public Mono<CommentResponse> addComment(@Valid @RequestBody CommentRequest commentRequest,
                                           @PathVariable("postId") UUID postId) {

        // check user from authorization header jwt

        // check if post exists - webclient call to posts
        // comment service add post
        return commentService.add(commentRequest, postId);
        // return post

    }

    @PutMapping(value = "/comments/{commentId}")
    public Mono<CommentResponse> editComment(@Valid @RequestBody CommentRequest commentRequest,
                                             @PathVariable("commentId") UUID commentId) {

        // check same user;
        return commentService.edit(commentRequest, commentId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/comments/{commentId}")
    public Mono<Void> deleteComment(@PathVariable("commentId") UUID commentId) {

        // check same user;
        return commentService.delete(commentId);
    }



}
