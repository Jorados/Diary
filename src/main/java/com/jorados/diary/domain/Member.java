package com.jorados.diary.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
    private String roles; //USER
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Post> post = new ArrayList<>();

    public List<String> getRoleList(){
        if(this.roles.length() > 0){
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }

    @Builder
    public Member(String username,String nickname,String password) {
        this.username = username;
        this.nickname = nickname;
        this.password = password;
    }

    public void EncodingAndRole(String password,String roles) {
        this.password = password;
        this.roles = roles;
    }


    public void edit(String username,String nickname, String password){
        this.username =username;
        this.nickname = nickname;
        this.password = password;
    }

}
