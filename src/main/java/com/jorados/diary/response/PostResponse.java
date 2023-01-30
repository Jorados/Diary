package com.jorados.diary.response;

import com.jorados.diary.domain.Post;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class PostResponse implements Serializable {
    private Long id;
    private String title;
    private String content;


    public PostResponse(Post post){
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
    }
    @Builder
    public PostResponse(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }
}
