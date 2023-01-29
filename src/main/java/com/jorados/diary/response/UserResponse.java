package com.jorados.diary.response;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class UserResponse {
    private Long id;
    private String userId;
    private String userPw;
    private String userName;
    @Builder
    public UserResponse(Long id, String userId, String userPw, String userName) {
        this.id = id;
        this.userId = userId;
        this.userPw = userPw;
        this.userName = userName;
    }
}
