//package com.jorados.diary.repository.member;
//
//import com.jorados.diary.domain.Member;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//
//import java.util.List;
//import java.util.Optional;
//
//public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {
//    Member findByUsername(String username);
//
//
//    List<Member> findAllByUsername(String username);
//
//    @Query("select m from Member m left join fetch m.post")
//    Member findMemberFetchJoin();
//
//    @Query("select m from Member m left join fetch m.post where m.id = :memberId")
//    Member findByMemberId(@Param("memberId") Long memberId);
//}
