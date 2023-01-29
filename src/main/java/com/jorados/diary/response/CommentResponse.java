package com.jorados.diary.response;

import com.jorados.diary.domain.Post;
import com.jorados.diary.domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentResponse {
    private String content;
    private User user;
    private Post post;

    @Builder
    public CommentResponse(String content, User user, Post post) {
        this.content = content;
        this.user = user;
        this.post = post;
    }

    @Builder
    public CommentResponse(String content, Post post) {
        this.content = content;
        this.post = post;
    }
}
