//package com.jorados.diary.serviceTest;
//
//import com.jorados.diary.domain.Member;
//import com.jorados.diary.domain.Post;
//import com.jorados.diary.exception.UserNotFound;
//import com.jorados.diary.repository.member.MemberRepository;
//import com.jorados.diary.repository.post.PostRepository;
//import com.jorados.diary.request.member.MemberEdit;
//import com.jorados.diary.response.MemberResponse;
//import com.jorados.diary.service.MemberService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import static org.assertj.core.api.Assertions.*;
//
//@SpringBootTest
//public class MemberServiceTest {
//    @BeforeEach
//    void deleteAll(){
//        memberRepository.deleteAll();
//    }
//    @Autowired MemberRepository memberRepository;
//    @Autowired MemberService memberService;
//
//    @Autowired PostRepository postRepository;
//    @Test
//    @DisplayName("회원가입")
//    public void test1() throws Exception {
//        //given
//        Member member = Member.builder()
//                .username("조성진")
//                .build();
//
//        Member member2 = Member.builder()
//                .username("조성진")
//                .build();
//        //when
//        memberService.join(member);
//        memberService.join(member2);
//        //then
//        fail("에러 발생해야한다.");
//    }
//
//    @Test
//    @DisplayName("회원 조회")
//    public void test2() throws Exception {
//        //given
//        Member member = Member.builder()
//                .username("성진")
//                .nickname("성진")
//                .password("성진")
//                .build();
//        memberRepository.save(member);
//
//        //when
//        MemberResponse findMember = memberService.read(member.getId());
//
//        //then
//        assertThat(memberRepository.findAll().size()).isEqualTo(1);
//        assertThat(findMember.getUsername()).isEqualTo("성진");
//        assertThat(findMember.getNickname()).isEqualTo("성진");
//    }
//
//    @Test
//    @DisplayName("회원 정보수정")
//    public void test3() throws Exception {
//        //given
//        Member member = Member.builder()
//                .username("성진")
//                .nickname("성진")
//                .password("성진")
//                .build();
//        memberRepository.save(member);
//
//        MemberEdit memberEdit = MemberEdit.builder()
//                .username("ssss")
//                .nickname("ssss")
//                .password("ssss")
//                .build();
//        //when
//        memberService.update(member.getId(), memberEdit);
//        //then
//        Member findMember = memberRepository.findById(member.getId()).orElseThrow(() -> new UserNotFound());
//        assertThat(findMember.getUsername()).isEqualTo("ssss");
//    }
//
//    @Test
//    @DisplayName("회원 삭제")
//    public void test4() throws Exception {
//        //given
//        Member member = Member.builder()
//                .username("성진")
//                .nickname("성진")
//                .password("성진")
//                .build();
//        memberRepository.save(member);
//        //when
//        Member findMember = memberRepository.findById(member.getId()).orElseThrow(() -> new UserNotFound());
//        memberService.delete(findMember.getId());
//        //then
//        assertThat(memberRepository.findAll().size()).isEqualTo(0);
//    }
//
//    @Test
//    @DisplayName("작성한 글 조회")
//    public void test5() throws Exception {
//        //given
//        Member member = Member.builder()
//                .username("seongjin")
//                .password("123")
//                .nickname("호랑이")
//                .build();
//
//        memberRepository.save(member);
//
//        Post post = Post.builder()
//                .title("하하")
//                .content("호호")
//                .member(member)
//                .build();
//
//        postRepository.save(post);
//
//        //when
//        Member findMember = memberRepository.findMemberFetchJoin();
//        //then
//        assertThat(findMember.getPost().size()).isEqualTo(1);
//    }
//
//    @Test
//    @DisplayName("작성한 글 조회-2")
//    public void test6() throws Exception {
//        //given
//        Member member = Member.builder()
//                .username("seongjin")
//                .password("123")
//                .nickname("호랑이")
//                .build();
//        memberRepository.save(member);
//
//        Post post = Post.builder()
//                .title("하하")
//                .content("호호")
//                .member(member)
//                .build();
//        postRepository.save(post);
//
//        //when
//        Member findMember = memberRepository.findByMemberId(member.getId());
//        //then
//        assertThat(findMember.getPost().size()).isEqualTo(1);
//    }
//}
