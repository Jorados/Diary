package com.jorados.diary.request.member;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class MemberCreate {
    @NotBlank
    private String username;
    @NotBlank
    private String loginId;
    @NotBlank
    private String password;

    @Builder
    public MemberCreate(String username, String loginId, String password) {
        this.username = username;
        this.loginId = loginId;
        this.password = password;
    }
}
