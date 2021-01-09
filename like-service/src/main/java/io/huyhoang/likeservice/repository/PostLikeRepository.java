package io.huyhoang.likeservice.repository;

import io.huyhoang.likeservice.entity.PostLike;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Repository
public interface PostLikeRepository extends R2dbcRepository<PostLike, UUID> {

    Flux<PostLike> findByPostId(UUID postId);
    Flux<PostLike> findByPostIdAndUserId(UUID postId, UUID userId);
}
