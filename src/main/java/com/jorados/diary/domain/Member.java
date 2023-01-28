package com.jorados.diary.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="member_id")
    private Long id;
    @NotBlank
    private String username;
    private String nickname;
    private String password;
    private String role; //USER
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Post> post = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Comment> comment = new ArrayList<>();

    @Builder
    public Member(String username,String nickname,String password,String role) {
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.role = role;
    }

//    public void EncodingAndRole(String password,String role) {
//        this.password = password;
//        this.role = role;
//    }

    public void edit(String username,String nickname, String password){
        this.username =username;
        this.nickname = nickname;
        this.password = password;
    }
}
