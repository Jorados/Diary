package com.jorados.diary.response;

import com.jorados.diary.domain.Post;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class MemberResponse {
    private Long id;
    private String username;
    private String nickname;
    private String password;

    @Builder
    public MemberResponse(Long id,String username, String nickname, String password) {
        this.id = id;
        this.username = username;
        this.nickname = nickname;
        this.password = password;
    }
}
