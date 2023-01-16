package com.jorados.diary.repository;

import com.jorados.diary.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long>,MemberRepositoryCustom {
    Member findByUsername(String username);
    List<Member> findAllByUsername(String username);
}
