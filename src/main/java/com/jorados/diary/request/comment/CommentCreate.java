package com.jorados.diary.request.comment;


import com.jorados.diary.domain.Post;
import com.jorados.diary.domain.User;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;


@Getter
public class CommentCreate {

    @NotBlank
    private String content;
    private User user;
    private Post post;

    @Builder
    public CommentCreate(String content, User user, Post post) {
        this.content = content;
        this.user = user;
        this.post = post;
    }
}
