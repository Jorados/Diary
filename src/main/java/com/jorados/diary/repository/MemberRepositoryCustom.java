package com.jorados.diary.repository;

import com.jorados.diary.domain.Member;

public interface MemberRepositoryCustom {
    <Optional> java.util.Optional<Member> findByLoginId(String loginId);
}
