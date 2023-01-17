package com.jorados.diary.controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jorados.diary.domain.Member;
import com.jorados.diary.domain.Post;
import com.jorados.diary.repository.MemberRepository;
import com.jorados.diary.repository.PostRepository;
import com.jorados.diary.service.MemberService;
import org.assertj.core.api.Assertions;
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

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class MemberControllerTest {
    @BeforeEach
    void deleteAll(){
        memberRepository.deleteAll();
    }
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    PostRepository postRepository;

    @Test
    @DisplayName("회원가입")
    @WithMockUser
    public void test1() throws Exception {
        //given
        Member member = Member.builder()
                .username("asd")
                .nickname("성진")
                .password("123")
                .build();
        String json = objectMapper.writeValueAsString(member);
        //when
        mockMvc.perform(post("/member")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .with(csrf())
                )
                .andExpect(status().isOk())
                .andDo(print());
        //then
        Assertions.assertThat(memberRepository.findAll().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("중복 회원가입")
    @WithMockUser
    public void test2() throws Exception {
        //given
        Member member = Member.builder()
                .username("asd")
                .nickname("성진")
                .password("123")
                .build();
        memberRepository.save(member);

        Member member2 = Member.builder()
                .username("asd")
                .nickname("성진")
                .password("123")
                .build();
        String json = objectMapper.writeValueAsString(member2);
        //when
        mockMvc.perform(post("/member")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .with(csrf())
                )
                .andExpect(status().isNotFound())
                .andDo(print());
        //then
    }

    @Test
    @DisplayName("내가 작성한 글 조회")
    @WithMockUser
    public void test3() throws Exception {
        //given
        Member member=Member.builder()
                .username("seongjin")
                .password("1234")
                .nickname("성진")
                .build();
        Member saveMember = memberRepository.save(member);

        Post post = Post.builder()
                .title("하하")
                .content("호호")
                .member(member)
                .build();
        postRepository.save(post);

        //when
        mockMvc.perform(get("/member/{memberId}/post",saveMember.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf())
                )
                .andExpect(status().isOk())
                .andDo(print());
        //then

    }



}
