package com.jorados.diary.response;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class MemberResponse {
    private Long id;
    private String username;
    private String loginId;
    private String password;

    @Builder
    public MemberResponse(Long id,String username, String loginId, String password) {
        this.id = id;
        this.username = username;
        this.loginId = loginId;
        this.password = password;
    }
}
