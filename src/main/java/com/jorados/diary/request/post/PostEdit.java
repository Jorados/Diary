package com.jorados.diary.request.post;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Data
public class PostEdit {

    @NotBlank
    private String title;
    @NotBlank
    private String content;

    @Builder
    public PostEdit(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
