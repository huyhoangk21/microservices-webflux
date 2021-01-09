package io.huyhoang.likeservice.dto;

import java.io.Serializable;
import java.util.UUID;

public class PostLikeResponse implements Serializable {

    private UUID likeId;

    private UUID postId;

    private UUID userId;

    public PostLikeResponse() {
    }

    public PostLikeResponse(UUID likeId, UUID postId, UUID userId) {
        this.likeId = likeId;
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
}
