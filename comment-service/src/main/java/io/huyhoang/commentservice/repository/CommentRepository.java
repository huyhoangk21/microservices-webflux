package io.huyhoang.commentservice.repository;

import io.huyhoang.commentservice.entity.Comment;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CommentRepository extends ReactiveCrudRepository<Comment, UUID> {

}
