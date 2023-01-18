package com.jorados.diary.response;

import com.jorados.diary.domain.Member;
import com.jorados.diary.domain.Post;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentResponse {
    private String content;
    private Member member;
    private Post post;

    @Builder
    public CommentResponse(String content, Member member, Post post) {
        this.content = content;
        this.member = member;
        this.post = post;
    }
}
