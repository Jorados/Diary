package com.jorados.diary.controller;


import com.jorados.diary.domain.Member;
import com.jorados.diary.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/join")
    public void join(@RequestBody @Valid Member member) throws Exception {
        memberService.join(member);
    }

    @GetMapping("/api/v1/member")
    public String member(){
        return "member";
    }
}
