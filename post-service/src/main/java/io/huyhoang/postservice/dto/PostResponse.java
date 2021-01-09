package io.huyhoang.postservice.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public class PostResponse implements Serializable {

    private UUID postId;

    private UUID userId;

    private String imageUrl;

    private String description;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public PostResponse() {
    }

    public PostResponse(UUID postId,
                        UUID userId,
                        String imageUrl,
                        String description,
                        LocalDateTime createdAt,
                        LocalDateTime updatedAt) {
        this.postId = postId;
        this.userId = userId;
        this.imageUrl = imageUrl;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
