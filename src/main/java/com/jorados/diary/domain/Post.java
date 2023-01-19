package com.jorados.diary.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Post {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="post_id")
    private Long id;
    private String title;
    @Lob
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Post(String title, String content,Member member) {
        this.title = title;
        this.content = content;
        this.member = member;
    }
    public void edit(String title,String content){
        this.title = title;
        this.content = content;
    }
}
