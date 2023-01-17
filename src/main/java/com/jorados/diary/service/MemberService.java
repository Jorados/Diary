package com.jorados.diary.service;

import com.jorados.diary.domain.Member;
import com.jorados.diary.domain.Post;
import com.jorados.diary.exception.DuplicateException;
import com.jorados.diary.exception.MemberNotFound;
import com.jorados.diary.repository.MemberRepository;
import com.jorados.diary.request.MemberEdit;
import com.jorados.diary.response.MemberResponse;
import com.jorados.diary.response.PostResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public void join(Member member){
        validateDuplicateMember(member);
        member.EncodingAndRole(bCryptPasswordEncoder.encode(member.getPassword()),"ROLE_USER");
        memberRepository.save(member);
        log.info("회원 가입 완료");
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findAllByUsername(member.getUsername());
        if (!findMembers.isEmpty()) {
            throw new DuplicateException();
        }
    }

    public MemberResponse read(Member member) {
        Member findMember = memberRepository.findById(member.getId()).orElseThrow(() -> new MemberNotFound());
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
        Member findMember = memberRepository.findById(memberId).orElseThrow(() -> new MemberNotFound());
        findMember.edit(
                memberEdit.getUsername() != null ? memberEdit.getUsername() : findMember.getUsername(),
                memberEdit.getLoginId() != null ? memberEdit.getLoginId() : findMember.getLoginId(),
                memberEdit.getPassword() != null ? memberEdit.getPassword() : findMember.getPassword()
        );
    }

    public void delete(Long memberId){
        Member findMember = memberRepository.findById(memberId).orElseThrow(() -> new MemberNotFound());
        memberRepository.delete(findMember);
    }



}
