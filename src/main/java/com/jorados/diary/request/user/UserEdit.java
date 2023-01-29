package com.jorados.diary.request.user;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class UserEdit {
    @NotBlank
    private String userId;
    @NotBlank
    private String userPw;
    @NotBlank
    private String userName;

    @Builder
    public UserEdit(String userId, String userPw, String userName) {
        this.userId = userId;
        this.userPw = userPw;
        this.userName = userName;
    }
}
