package com.jorados.diary.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class MemberEdit {
    @NotBlank
    private String username;
    @NotBlank
    private String loginId;
    @NotBlank
    private String password;

    @Builder
    public MemberEdit(String username, String loginId, String password) {
        this.username = username;
        this.loginId = loginId;
        this.password = password;
    }
}
