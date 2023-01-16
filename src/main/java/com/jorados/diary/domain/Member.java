package com.jorados.diary.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String LoginId;
    private String password;
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Post> post;
}
