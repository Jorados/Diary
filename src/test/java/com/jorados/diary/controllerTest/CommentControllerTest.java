package com.jorados.diary.controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jorados.diary.repository.comment.CommentRepository;
import com.jorados.diary.repository.member.MemberRepository;
import com.jorados.diary.repository.post.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@SpringBootTest
public class CommentControllerTest {
    @BeforeEach
    void deleteAll() { commentRepository.deleteAll();}
    @Autowired ObjectMapper objectMapper;
    @Autowired MockMvc mockMvc;
    @Autowired CommentRepository commentRepository;
    @Autowired MemberRepository memberRepository;
    @Autowired PostRepository postRepository;

    @Test
    @DisplayName("댓글 작성 후 댓글 작성자 , 댓글 글 조회")
    @WithMockUser
    public void test1() throws Exception {
        //given

        //when
        //then
    }
}
