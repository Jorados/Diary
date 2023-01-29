package com.jorados.diary.controller;

/* web / UserController.java */
import com.jorados.diary.domain.User;
import com.jorados.diary.request.member.MemberEdit;
import com.jorados.diary.request.user.UserEdit;
import com.jorados.diary.response.MemberResponse;
import com.jorados.diary.response.UserResponse;
import com.jorados.diary.service.UserService;
import com.jorados.diary.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/user")
//로그인 메서드 구현
public class UserController {
    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    //login
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> paramMap) {
        String userId = paramMap.get("user_id");
        String userPw = paramMap.get("user_pw");

        UserDetails loginUser = userService.loadUserByUsername(userId); //1.userId로 정보 가져오기

        Authentication authentication = authenticationManager.authenticate(     //2.가져온 정보와 입력한 비밀번호로 검증
                new UsernamePasswordAuthenticationToken(loginUser, userPw)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);   //3.검증 통과 후 authentication 세팅

        String accessToken = jwtUtil.createToken(loginUser.getUsername(), loginUser.getUsername());     //4.accessToken 생성

        Map<String, Object> result = new HashMap<>();
        result.put("user_id", loginUser.getUsername());
        result.put("user_token", accessToken);
        result.put("user_role", loginUser.getAuthorities().stream().findFirst().get().getAuthority());

        return ResponseEntity.ok(result);
    }

    //create
    @PostMapping("/join")
    public void join(@RequestBody @Valid User user) throws Exception {
        userService.join(user);
    }

    //read
    @GetMapping("/{userId}")
    public UserResponse read(@PathVariable Long userId){
        return userService.read(userId);
    }

    //update
    @PatchMapping("/{userId}")
    public void update(@PathVariable Long userId, UserEdit userEdit){
        userService.update(userId,userEdit);
    }

    //delete
    @DeleteMapping("/{userId}")
    public void delete(@PathVariable Long userId){
        userService.delete(userId);
    }


}
