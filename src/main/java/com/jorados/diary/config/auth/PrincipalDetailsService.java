package com.jorados.diary.config.auth;

import com.jorados.diary.domain.Member;
import com.jorados.diary.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


//http://localhost:8080/login => 여기서 동작을 안한다.
@Service
@RequiredArgsConstructor
@Slf4j
public class PrincipalDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("PrincipalDetailsService의 loadUserByUsername()");
        Member memberEntity = memberRepository.findByUsername(username);
        log.info("memberEntity={}",memberEntity);
        return new PrincipalDetails(memberEntity);
    }
}
