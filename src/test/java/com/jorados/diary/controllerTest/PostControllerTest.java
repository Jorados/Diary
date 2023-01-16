package com.jorados.diary.controllerTest;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.jorados.diary.domain.Post;
import com.jorados.diary.repository.PostRepository;
import com.jorados.diary.request.PostCreate;
import com.jorados.diary.request.PostEdit;
import com.jorados.diary.service.PostService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@SpringBootTest
public class PostControllerTest {

    @BeforeEach
    void deleteAll(){
        postRepository.deleteAll();
    }

    @Autowired
    PostRepository postRepository;
    @Autowired
    PostService postService;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("게시글 create 요청")
    @WithMockUser
    public void test() throws Exception {
        //given
        PostCreate request = PostCreate.builder()
                .title("제목")
                .content("내용")
                .build();

        String json = objectMapper.writeValueAsString(request);
        //when
        //then
        mockMvc.perform(post("/posts")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                                .with(csrf())
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("게시글 create 데이터 검증")
    @WithMockUser
    public void test2() throws Exception {
        //given
        PostCreate postCreate = PostCreate.builder()
                .title("제목")
                .build();
        //when
        String json = objectMapper.writeValueAsString(postCreate);

        mockMvc.perform(post("/posts")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                                .with(csrf())
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.validation.content").value("제목을 입력해주세요"))
                .andDo(print());
    }

    @Test
    @DisplayName("post create 후 db정보 확인")
    @WithMockUser
    public void test3() throws Exception {
        //given
        PostCreate postCreate = PostCreate.builder()
                .title("제목")
                .content("내용")
                .build();

        String json = objectMapper.writeValueAsString(postCreate);
        //when
        mockMvc.perform(post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .with(csrf()))
                .andExpect(status().isOk())
                .andDo(print());
        //then
        assertThat(postRepository.findAll().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("글 1개 조회")
    @WithMockUser
    public void tes4() throws Exception {
        //given
        Post post = Post.builder()
                .title("제목")
                .content("내용")
                .build();

        postRepository.save(post);
        //when
        mockMvc.perform(get("/posts/{postId}",post.getId())
                .contentType(MediaType.APPLICATION_JSON)
                                .with(csrf())
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(post.getId()))
                .andExpect(jsonPath("$.title").value("제목"))
                .andExpect(jsonPath("$.content").value("내용"))
                .andDo(print());
        //then
    }

    @Test
    @DisplayName("글 여러개 조회")
    @WithMockUser
    public void test5() throws Exception {
        //given
        List<Post> CreatePosts = IntStream.range(0, 20)
                .mapToObj(i -> Post.builder()
                        .title("제목" + i)
                        .content("내용" + i)
                        .build())
                .collect(Collectors.toList());
        postRepository.saveAll(CreatePosts);

        //when
        mockMvc.perform(get("/posts?page=1&size=10")
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", Matchers.is(10)))
                .andExpect(jsonPath("$[0].title").value("제목19"))
                .andExpect(jsonPath("$.[0].content").value("내용19"))
                .andDo(print());
        //then
    }

    @Test
    @DisplayName("글 수정 테스트")
    @WithMockUser
    public void test6() throws Exception {
        //given
        Post post = Post.builder()
                .title("제목")
                .content("내용")
                .build();

        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title("나는 성진")
                .content("나는 성진")
                .build();
        //when
        mockMvc.perform(patch("/posts/{postId}",post.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postEdit))
                .with(csrf())
        )
                .andExpect(status().isOk())
                .andDo(print());
        Post findPost = postRepository.findById(post.getId()).orElseThrow(() -> new RuntimeException("글이 존재하지 않습니다. id " + post.getId()));
        assertThat(findPost.getContent()).isEqualTo("나는 성진");
        //then
    }

    @Test
    @DisplayName("글 삭제 테스트")
    @WithMockUser
    public void test7() throws Exception {
        //given
        Post post = Post.builder()
                .title("제목")
                .content("내용")
                .build();
        postRepository.save(post);
        //when
        mockMvc.perform(delete("/posts/{postId}", post.getId())
                        .contentType(APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andDo(print());

        //then
        assertThat(postRepository.findAll().size()).isEqualTo(0);
    }




}
