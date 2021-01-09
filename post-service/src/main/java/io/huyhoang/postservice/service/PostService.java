package io.huyhoang.postservice.service;

import io.huyhoang.postservice.dto.PostRequest;
import io.huyhoang.postservice.dto.PostResponse;
import io.huyhoang.postservice.entity.Post;
import io.huyhoang.postservice.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Transactional(readOnly = true)
    public Flux<PostResponse> allByUser(UUID userId) {
        return postRepository.findByUserIdOrderByCreatedAtDesc(userId)
                .flatMap(this::convertDTO);
    }

    @Transactional
    public Mono<PostResponse> add(PostRequest postRequest, UUID userId) {
        // Amazon S3 to store imageUrl
        return postRepository.save(new Post(userId, postRequest.getImageUrl(), postRequest.getDescription()))
                .flatMap(this::convertDTO);
    }

    @Transactional
    public Mono<PostResponse> edit(PostRequest postRequest, UUID postId) {
        // check user id and jwt to allow
        return postRepository.findById(postId)
                .switchIfEmpty(Mono.error(RuntimeException::new))
                .flatMap(post -> {
                    post.setDescription(postRequest.getDescription());
                    return postRepository.save(post);
                })
                .flatMap(this::convertDTO);
    }

    @Transactional
    public Mono<Void> delete(UUID postId) {
        // check user id and jwt to allow
        return postRepository.findById(postId)
                .switchIfEmpty(Mono.error(RuntimeException::new))
                .flatMap(postRepository::delete);
    }

    private Mono<PostResponse>  convertDTO(Post post) {
        return Mono.just(new PostResponse(
                post.getPostId(),
                post.getUserId(),
                post.getImageUrl(),
                post.getDescription(),
                post.getCreatedAt(),
                post.getUpdatedAt()));
    }

}
