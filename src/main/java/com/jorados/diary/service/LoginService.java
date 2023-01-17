package com.jorados.diary.service;

import com.jorados.diary.domain.Member;
import com.jorados.diary.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;

}
