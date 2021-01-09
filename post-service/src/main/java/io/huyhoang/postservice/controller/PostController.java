package io.huyhoang.postservice.controller;

import io.huyhoang.postservice.dto.PostRequest;
import io.huyhoang.postservice.dto.PostResponse;
import io.huyhoang.postservice.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping(value = "/users/{userId}/posts")
    public Flux<PostResponse> allPostsByUser(@PathVariable("userId") UUID userId) {
        return postService.allByUser(userId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/users/{userId}/posts")
    public Mono<PostResponse> addPost(@Valid @RequestBody PostRequest postRequest,
                                      @PathVariable("userId") UUID userId) {
        return postService.add(postRequest, userId);
    }

    @PutMapping(value = "/posts/{postId}")
    public Mono<PostResponse> editPost(@Valid @RequestBody PostRequest postRequest,
                                      @PathVariable("postId") UUID postId) {
        return postService.edit(postRequest, postId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/posts/{postId}")
    public Mono<Void> editPost(@PathVariable("postId") UUID postId) {
        return postService.delete(postId);
    }

}
