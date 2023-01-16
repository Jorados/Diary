package com.jorados.diary.serviceTest;

import com.jorados.diary.domain.Member;
import com.jorados.diary.domain.Post;
import com.jorados.diary.repository.MemberRepository;
import com.jorados.diary.request.MemberEdit;
import com.jorados.diary.response.MemberResponse;
import com.jorados.diary.response.PostResponse;
import com.jorados.diary.service.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class MemberServiceTest {
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemberService memberService;
    @Test
    @DisplayName("회원가입")
    public void test1() throws Exception {
        //given
        Member member = Member.builder()
                .username("조성진")
                .build();

        Member member2 = Member.builder()
                .username("조성진")
                .build();
        //when
        memberService.join(member);
        memberService.join(member2);
        //then
        fail("에러 발생해야한다.");
    }

    @Test
    @DisplayName("회원 조회")
    public void test2() throws Exception {
        //given
        Member member = Member.builder()
                .username("성진")
                .loginId("성진")
                .password("성진")
                .build();
        memberRepository.save(member);

        //when
        MemberResponse findMember = memberService.read(member);

        //then
        assertThat(memberRepository.findAll().size()).isEqualTo(1);
        assertThat(findMember.getUsername()).isEqualTo("성진");
        assertThat(findMember.getLoginId()).isEqualTo("성진");
    }

    @Test
    @DisplayName("회원 정보수정")
    public void test3() throws Exception {
        //given
        Member member = Member.builder()
                .username("성진")
                .loginId("성진")
                .password("성진")
                .build();
        memberRepository.save(member);

        MemberEdit memberEdit = MemberEdit.builder()
                .username("ssss")
                .loginId("ssss")
                .password("ssss")
                .build();
        //when
        memberService.update(member.getId(), memberEdit);
        //then
        Member findMember = memberRepository.findById(member.getId()).orElseThrow(() -> new IllegalArgumentException());
        assertThat(findMember.getUsername()).isEqualTo("ssss");
    }

    @Test
    @DisplayName("회원 삭제")
    public void test4() throws Exception {
        //given
        Member member = Member.builder()
                .username("성진")
                .loginId("성진")
                .password("성진")
                .build();
        memberRepository.save(member);
        //when
        Member findMember = memberRepository.findById(member.getId()).orElseThrow(() -> new IllegalArgumentException());
        memberService.delete(findMember.getId());
        //then
        assertThat(memberRepository.findAll().size()).isEqualTo(0);
    }
}
