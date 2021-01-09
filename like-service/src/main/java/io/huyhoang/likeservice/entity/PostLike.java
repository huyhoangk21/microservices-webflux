package io.huyhoang.likeservice.entity;


import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("post_likes")
public class PostLike {

    @Id
    @Column("like_id")
    private UUID likeId;

    @Column("post_id")
    private UUID postId;

    @Column("user_id")
    private UUID userId;

    public PostLike() {
    }

    public PostLike(UUID postId, UUID userId) {
        this.postId = postId;
        this.userId = userId;
    }

    public UUID getLikeId() {
        return likeId;
    }

    public void setLikeId(UUID likeId) {
        this.likeId = likeId;
    }

    public UUID getPostId() {
        return postId;
    }

    public void setPostId(UUID postId) {
        this.postId = postId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "PostLike{" +
                "likeId=" + likeId +
                ", postId=" + postId +
                ", userId=" + userId +
                '}';
    }
}
