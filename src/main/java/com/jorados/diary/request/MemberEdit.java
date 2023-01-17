package com.jorados.diary.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class MemberEdit {
    @NotBlank
    private String username;
    @NotBlank
    private String nickname;
    @NotBlank
    private String password;

    @Builder
    public MemberEdit(String username, String nickname, String password) {
        this.username = username;
        this.nickname = nickname;
        this.password = password;
    }
}
