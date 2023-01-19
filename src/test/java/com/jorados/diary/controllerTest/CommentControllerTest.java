package com.jorados.diary.controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jorados.diary.domain.Comment;
import com.jorados.diary.domain.Member;
import com.jorados.diary.domain.Post;
import com.jorados.diary.repository.comment.CommentRepository;
import com.jorados.diary.repository.member.MemberRepository;
import com.jorados.diary.repository.post.PostRepository;
import com.jorados.diary.request.comment.CommentCreate;
import com.jorados.diary.service.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    @Autowired
    CommentService commentService;

    @Test
    @DisplayName("댓글 생성")
    @WithMockUser
    public void test1() throws Exception {
        //given
        CommentCreate commentCreate = CommentCreate.builder()
                        .content("댓글1")
                        .build();

        String json = objectMapper.writeValueAsString(commentCreate);

        //when
        mockMvc.perform(post("/comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andDo(print());
        //then
    }

    @Test
    @DisplayName("댓글 조회")
    @WithMockUser
    public void test2() throws Exception {
        //given
        Comment comment = Comment.builder()
                .content("첫 댓글")
                .build();

        commentRepository.save(comment);

        //when
        mockMvc.perform(get("/comment/{commentId}",comment.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("첫 댓글"))
                .andDo(print());
        //then
    }


}
