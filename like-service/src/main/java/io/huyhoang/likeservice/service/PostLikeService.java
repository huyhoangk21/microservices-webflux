package io.huyhoang.likeservice.service;

import io.huyhoang.likeservice.dto.PostLikeResponse;
import io.huyhoang.likeservice.entity.PostLike;
import io.huyhoang.likeservice.repository.PostLikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;

    @Autowired
    public PostLikeService(PostLikeRepository postLikeRepository) {
        this.postLikeRepository = postLikeRepository;
    }


    @Transactional(readOnly = true)
    public Flux<PostLikeResponse> allByPost(UUID postId) {
        return postLikeRepository.findByPostId(postId)
                .flatMap(this::convertDTO);
    }

    @Transactional
    public Flux<PostLikeResponse> likePost(UUID postId, UUID userId) {
        return postLikeRepository.findByPostIdAndUserId(postId, userId)
                .flatMap(postLike -> Mono.error(RuntimeException::new))// not empty
                .switchIfEmpty(postLikeRepository.save(new PostLike(postId, userId)))
                .thenMany(postLikeRepository.findByPostId(postId))
                .flatMap(this::convertDTO);

    }

    @Transactional
    public Flux<PostLikeResponse> unlikePost(UUID postId, UUID userId) {
        return postLikeRepository.findByPostIdAndUserId(postId, userId)
                .switchIfEmpty(Mono.error(RuntimeException::new))
                .flatMap(postLikeRepository::delete)
                .thenMany(postLikeRepository.findByPostId(postId))
                .flatMap(this::convertDTO);
    }


    private Mono<PostLikeResponse> convertDTO(PostLike postLike) {
        return Mono.just(new PostLikeResponse(
                postLike.getLikeId(),
                postLike.getPostId(),
                postLike.getUserId()));
    }
}
