package com.jorados.diary.request.post;


import lombok.*;
import org.springframework.security.core.userdetails.User;

import javax.validation.constraints.NotBlank;


@Data
@NoArgsConstructor
public class PostCreate {

    @NotBlank(message = "타이틀을 입력해주세요")
    private String title;

    @NotBlank(message = "제목을 입력해주세요")
    private String content;

    private User user;

    @Builder
    public PostCreate(String title, String content,User user) {
        this.title = title;
        this.content = content;
        this.user = user;
    }
}
