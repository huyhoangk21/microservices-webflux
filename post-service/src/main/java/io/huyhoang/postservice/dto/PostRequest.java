package io.huyhoang.postservice.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class PostRequest implements Serializable {

    @NotBlank(message = "Image url must not be empty")
    private String imageUrl;

    @NotBlank(message = "Description must not be empty")
    @Size(min = 6, message = "Description must be more than or equal to 6 characters")
    private String description;


    public PostRequest() {
    }

    public PostRequest(String imageUrl, String description) {
        this.imageUrl = imageUrl;
        this.description = description;
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
}
