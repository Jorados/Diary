package com.jorados.diary.service;

import com.jorados.diary.domain.User;
import com.jorados.diary.exception.DuplicateException;
import com.jorados.diary.exception.UserNotFound;
import com.jorados.diary.repository.user.UserRepository;
import com.jorados.diary.request.user.UserEdit;
import com.jorados.diary.response.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<GrantedAuthority> authorities = new ArrayList<>();

        User userEntity = userRepository.findByUserId(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        if (userEntity.getUserId().equals(username)) {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }

        return new org.springframework.security.core.userdetails.User(userEntity.getUserId(), userEntity.getUserPw(), authorities);
    }

    @Transactional
    public void join(User user){
        validateDuplicateMember(user);

        String encPassword = passwordEncoder.encode(user.getUserPw());
        User saveUser = User.builder()
                .userId(user.getUserId())
                .userPw(encPassword)
                .userName(user.getUserName())
                .build();
        userRepository.save(saveUser);
        log.info("회원 가입 완료");
    }

    private void validateDuplicateMember(User user) {
        List<User> findAllUser = userRepository.findAllByUserName(user.getUserName());
        List<User> findAllUserId = userRepository.findAllByUserId(user.getUserId());

        //userId겹치는경우
        if(!findAllUserId.isEmpty()){
            log.info("회원아이디 중복 회원 발생");
            throw new DuplicateException();
        }
        //userName겹치는 경우
        if (!findAllUser.isEmpty()) {
            log.info("회원이름 중복 회원 발생");
            throw new DuplicateException();
        }
    }

    public UserResponse read(Long userId) {
        User findUser = userRepository.findById(userId).orElseThrow(() -> new UserNotFound());
        UserResponse userResponse = UserResponse.builder()
                .id(findUser.getId())
                .userId(findUser.getUserId())
                .userPw(findUser.getUserPw())
                .userName(findUser.getUserName())
                .build();
        return userResponse;
    }

    @Transactional
    public void update(Long userId, UserEdit userEdit) {
        User findUser = userRepository.findById(userId).orElseThrow(() -> new UserNotFound());
        findUser.edit(
                userEdit.getUserId() != null ? userEdit.getUserId() : findUser.getUserId(),
                userEdit.getUserPw() != null ? userEdit.getUserPw() : findUser.getUserPw(),
                userEdit.getUserName() != null ? userEdit.getUserName() : findUser.getUserName()
        );
    }

    public void delete(Long userId){
        User findUser = userRepository.findById(userId).orElseThrow(() -> new UserNotFound());
        userRepository.delete(findUser);
    }
}
