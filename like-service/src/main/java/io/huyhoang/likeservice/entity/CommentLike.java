package io.huyhoang.likeservice.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("comment_likes")
public class CommentLike {

    @Id
    @Column("like_id")
    private UUID likeId;

    @Column("comment_id")
    private UUID commentId;

    @Column("user_id")
    private UUID userId;

    public CommentLike() {
    }

    public CommentLike(UUID commentId, UUID userId) {
        this.commentId = commentId;
        this.userId = userId;
    }

    public UUID getLikeId() {
        return likeId;
    }

    public void setLikeId(UUID likeId) {
        this.likeId = likeId;
    }

    public UUID getCommentId() {
        return commentId;
    }

    public void setCommentId(UUID commentId) {
        this.commentId = commentId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "CommentLike{" +
                "likeId=" + likeId +
                ", commentId=" + commentId +
                ", userId=" + userId +
                '}';
    }
}
