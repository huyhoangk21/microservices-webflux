package io.huyhoang.likeservice.controller;

import io.huyhoang.likeservice.dto.PostLikeResponse;
import io.huyhoang.likeservice.service.PostLikeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api/posts/{postId}/likes")
public class PostLikeController {

    private final PostLikeService postLikeService;

    public PostLikeController(PostLikeService postLikeService) {
        this.postLikeService = postLikeService;
    }

    @GetMapping
    public Flux<PostLikeResponse> allByPost(@PathVariable("postId") UUID postId) {
        return postLikeService.allByPost(postId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Flux<PostLikeResponse> like(@PathVariable("postId") UUID postId) {
        // get user from jwt
        return postLikeService.likePost(postId, UUID.fromString("dd5b78c1-2c71-49fd-ab48-befdcbe1fa85"));
    }


    @DeleteMapping
    public Flux<PostLikeResponse> unlike(@PathVariable("postId") UUID postId) {
        // get user from jwt
        return postLikeService.unlikePost(postId, UUID.fromString("dd5b78c1-2c71-49fd-ab48-befdcbe1fa85"));
    }
}
