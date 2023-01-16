package com.jorados.diary.service;

import com.jorados.diary.domain.Member;
import com.jorados.diary.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;

    //해당 로그인 아이디 찾아서 비밀번호랑 일치하면 로그인
    //없거나 안맞으면 null 리턴

    //controller에서 이제 로그인 성공하면 --> 세션이나,토큰 생성해서 응답해주고 로그인 페이지로~
    public Member login(String loginId, String password) {
        return memberRepository.findByLoginId(loginId)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null);
    }
}
