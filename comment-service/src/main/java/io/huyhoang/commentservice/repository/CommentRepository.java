package io.huyhoang.commentservice.repository;

import io.huyhoang.commentservice.entity.Comment;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Repository
public interface CommentRepository extends R2dbcRepository<Comment, UUID> {

    @Query("SELECT * FROM comments WHERE post_id = :postId ORDER BY DESC created_at")
    Flux<Comment> findAllCommentsByPost(UUID postId);
}
