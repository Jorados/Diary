package com.jorados.diary.request.comment;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentEdit {
    private String content;

    @Builder
    public CommentEdit(String content) {
        this.content = content;
    }
}
