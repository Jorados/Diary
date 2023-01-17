package com.jorados.diary.repository;


import com.jorados.diary.domain.Member;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {

    //private final MemberRepository memberRepository;

//    @Override
//    public Optional<Member> findByLoginId(String loginId) {
//        return memberRepository.findAll().stream()
//                .filter(m -> m.getLoginId().equals(loginId))
//                .findFirst();
//    }
}
