package com.jorados.diary.service;

import com.jorados.diary.domain.Member;
import com.jorados.diary.domain.Post;
import com.jorados.diary.repository.MemberRepository;
import com.jorados.diary.request.MemberEdit;
import com.jorados.diary.response.MemberResponse;
import com.jorados.diary.response.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public void join(Member member){
        validateDuplicateMember(member);
        memberRepository.save(member);
    }
    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findAllByUsername(member.getUsername());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    public MemberResponse read(Member member) {
        Member findMember = memberRepository.findById(member.getId()).orElseThrow(() -> new IllegalArgumentException());
        MemberResponse memberResponse = MemberResponse.builder()
                .id(findMember.getId())
                .username(findMember.getUsername())
                .loginId(findMember.getLoginId())
                .password(findMember.getPassword())
                .build();
        return memberResponse;
    }

    @Transactional
    public void update(Long memberId, MemberEdit memberEdit) {
        Member findMember = memberRepository.findById(memberId).orElseThrow(() -> new RuntimeException());
        findMember.edit(
                memberEdit.getUsername() != null ? memberEdit.getUsername() : findMember.getUsername(),
                memberEdit.getLoginId() != null ? memberEdit.getLoginId() : findMember.getLoginId(),
                memberEdit.getPassword() != null ? memberEdit.getPassword() : findMember.getPassword()
        );
    }

    public void delete(Long memberId){
        Member findMember = memberRepository.findById(memberId).orElseThrow(() -> new IllegalStateException());
        memberRepository.delete(findMember);
    }



}
