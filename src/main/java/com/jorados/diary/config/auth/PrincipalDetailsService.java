package com.jorados.diary.config.auth;

import com.jorados.diary.domain.Member;
import com.jorados.diary.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PrincipalDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("PrincipalDetailsService의 loadUserByUsername()");
        Member memberEntity = memberRepository.findByUsername(username);
//        Member memberEntity = memberRepository.findByUserId(username);
        log.info("memberEntity={}",memberEntity);
        return new PrincipalDetails(memberEntity);
    }

}
