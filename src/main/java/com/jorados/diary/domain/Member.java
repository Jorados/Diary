package com.jorados.diary.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String loginId;
    private String password;
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Post> post;

    @Builder
    public Member(String username, String loginId, String password) {
        this.username = username;
        this.loginId = loginId;
        this.password = password;
    }

    public void edit(String username,String loginId,String password){
        this.username =username;
        this.loginId = loginId;
        this.password = password;
    }
}
