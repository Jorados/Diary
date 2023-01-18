package com.jorados.diary.request.comment;


import com.jorados.diary.domain.Member;
import com.jorados.diary.domain.Post;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;


@Getter
public class CommentCreate {

    @NotBlank
    private String content;
    private Member member;
    private Post post;

    @Builder
    public CommentCreate(String content, Member member, Post post) {
        this.content = content;
        this.member = member;
        this.post = post;
    }
}
