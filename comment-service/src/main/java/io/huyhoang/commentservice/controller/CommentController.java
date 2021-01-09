package io.huyhoang.commentservice.controller;

import io.huyhoang.commentservice.dto.CommentDTO;
import io.huyhoang.commentservice.entity.Comment;
import io.huyhoang.commentservice.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/posts/{postId}/comments")
public class CommentController {

    private final CommentService commentService;
    Logger log = LoggerFactory.getLogger(CommentController.class);

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public Mono<Comment> addComment(@Valid @RequestBody CommentDTO commentDTO,
                                    @PathVariable("postId") UUID postId) {

        // check user from authorization header jwt

        // check if post exists - webclient call to posts

        // comment service add post
        Mono<Comment> res = commentService.addComment(commentDTO, postId);
        // return post
        log.info("{}", res);

        return res;

    }


}
