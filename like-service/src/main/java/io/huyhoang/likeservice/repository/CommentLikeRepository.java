package io.huyhoang.likeservice.repository;

import io.huyhoang.likeservice.entity.CommentLike;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CommentLikeRepository extends R2dbcRepository<CommentLike, UUID> {
}
