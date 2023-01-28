package com.jorados.diary.controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jorados.diary.controller.MemberController;
import com.jorados.diary.domain.Member;
import com.jorados.diary.domain.Post;
import com.jorados.diary.repository.member.MemberRepository;
import com.jorados.diary.repository.post.PostRepository;
import net.minidev.json.JSONObject;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

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
    MemberController memberController;

    @Autowired
    PostRepository postRepository;

    @Test
    @DisplayName("회원가입")
    public void test1() throws Exception {
        //given
        Member member = Member.builder()
                .username("asd")
                .nickname("성진")
                .password("123")
                .build();
        String json = objectMapper.writeValueAsString(member);
        //when
        mockMvc.perform(post("/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
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
        memberRepository.save(member);

        List<Post> CreatePosts = IntStream.range(1, 4)//1~3
                .mapToObj(i -> Post.builder()
                        .title("하하" + i)
                        .content("호호" + i)
                        .member(member)
                        .build())
                .collect(Collectors.toList());
        postRepository.saveAll(CreatePosts);

        //when
        mockMvc.perform(get("/member/{memberId}/post",member.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf())
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("하하1"))
                .andExpect(jsonPath("$[0].content").value("호호1"))
                .andDo(print());
        //then
    }

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(memberController).build();
    }

    @DisplayName("1. 로그인 실패 테스트")
    @Test
    void test_1() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("user_id", "test_userr");
        jsonObject.put("user_pw", "test_passwordd");

        ResultActions result = mockMvc.perform(post("/login")
                .content(jsonObject.toString())
                .contentType(MediaType.APPLICATION_JSON));

        MvcResult mvcResult = result.andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @DisplayName("2. 로그인 성공 테스트")
    @Test
    void test_2() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("user_id", "test_user");
        jsonObject.put("user_pw", "test_password");

        ResultActions result = mockMvc.perform(post("/login")
                .content(jsonObject.toString())
                .contentType(MediaType.APPLICATION_JSON));

        MvcResult mvcResult = result.andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }

}
