package com.jorados.diary.controller;


import com.jorados.diary.domain.Member;
import com.jorados.diary.request.member.MemberEdit;
import com.jorados.diary.response.MemberResponse;
import com.jorados.diary.response.PostResponse;
import com.jorados.diary.service.MemberService;
import com.jorados.diary.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final PostService postService;
    @PostMapping("/join")
    public void join(@RequestBody @Valid Member member) throws Exception {
        memberService.join(member);
    }

    @GetMapping("/member/{memberId}")
    public MemberResponse read(@PathVariable Long memberId){
        return memberService.read(memberId);
    }

    //내가 쓴 글
    @GetMapping("/member/{memberId}/post")
    public List<PostResponse> myPostRead(@PathVariable Long memberId) {
        List<PostResponse> findPosts = postService.findByMemberId(memberId);
        return findPosts;
    }

    @PatchMapping("/member/{memberId}")
    public void update(@PathVariable Long memberId, MemberEdit memberEdit){
        memberService.update(memberId,memberEdit);
    }

    @DeleteMapping("/member/{memberId}")
    public void delete(@PathVariable Long memberId){
        memberService.delete(memberId);
    }

    @GetMapping("/api/v1/member")
    public String member(){
        return "member";
    }
}
