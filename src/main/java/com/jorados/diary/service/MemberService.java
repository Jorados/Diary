//package com.jorados.diary.service;
//
//import com.jorados.diary.domain.Member;
//import com.jorados.diary.exception.DuplicateException;
//import com.jorados.diary.exception.UserNotFound;
//import com.jorados.diary.repository.member.MemberRepository;
//import com.jorados.diary.request.member.MemberEdit;
//import com.jorados.diary.response.MemberResponse;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import javax.transaction.Transactional;
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
//public class MemberService {
//    private final MemberRepository memberRepository;
//    private final BCryptPasswordEncoder bCryptPasswordEncoder;
//
//    @Transactional
//    public void join(Member member){
//        validateDuplicateMember(member);
//        Member saveMember = Member.builder()
//                        .username(member.getUsername())
//                        .password(bCryptPasswordEncoder.encode(member.getPassword()))
//                        .role("ROLE_USER")
//                        .nickname(member.getNickname())
//                        .build();
//        memberRepository.save(saveMember);
//        log.info("회원 가입 완료");
//    }
//
//    private void validateDuplicateMember(Member member) {
//        List<Member> findMembers = memberRepository.findAllByUsername(member.getUsername());
//        if (!findMembers.isEmpty()) {
//            log.info("중복 회원 발생");
//            throw new DuplicateException();
//        }
//    }
//
//    public MemberResponse read(Long memberId) {
//        Member findMember = memberRepository.findById(memberId).orElseThrow(() -> new UserNotFound());
//        MemberResponse memberResponse = MemberResponse.builder()
//                .id(findMember.getId())
//                .username(findMember.getUsername())
//                .nickname(findMember.getNickname())
//                .password(findMember.getPassword())
//                .build();
//        return memberResponse;
//    }
//
//    //내가 쓴 글 조회
//    public Member MyPostRead(Long memberId){
//        //--> memberId를 받아서 특정회원 찾는 쿼리로 만들어야됨
//        Member findMember = memberRepository.findByMemberId(memberId);
//        return findMember;
//    }
//
//
//
//    @Transactional
//    public void update(Long memberId, MemberEdit memberEdit) {
//        Member findMember = memberRepository.findById(memberId).orElseThrow(() -> new UserNotFound());
//        findMember.edit(
//                memberEdit.getUsername() != null ? memberEdit.getUsername() : findMember.getUsername(),
//                memberEdit.getNickname() != null ? memberEdit.getNickname() : findMember.getNickname(),
//                memberEdit.getPassword() != null ? memberEdit.getPassword() : findMember.getPassword()
//        );
//    }
//
//    public void delete(Long memberId){
//        Member findMember = memberRepository.findById(memberId).orElseThrow(() -> new UserNotFound());
//        memberRepository.delete(findMember);
//    }
//
//
//
//}
