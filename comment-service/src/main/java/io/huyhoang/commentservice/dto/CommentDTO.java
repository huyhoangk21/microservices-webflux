package io.huyhoang.commentservice.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class CommentDTO implements Serializable {

    @NotBlank(message = "Content must not be empty")
    @Size(max = 255, message = "Content must be less than or equal to 255 characters")
    private String content;

    public CommentDTO(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
