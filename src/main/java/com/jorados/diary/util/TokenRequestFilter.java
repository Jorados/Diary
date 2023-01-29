package com.jorados.diary.util;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.jorados.diary.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//로그인 이후에 생성된 토큰은 화면에서 서버로 요청할 때 항상 있어야 합니다.
//또한, 서버는 매번 받은 요청에서 토큰을 꺼내 확인해야 합니다.
//매번 컨트롤러에서 토큰을 확인하는 것은 반복적인 코드가 발생하므로 필터에서 작업하도록 하겠습니다.
//스프링의 필터 클래스를 사용하면 요청이 들어왔을 때 앞단에서 처리할 수 있습니다.
@Slf4j
@RequiredArgsConstructor
@Component
public class TokenRequestFilter extends OncePerRequestFilter {
    private final UserService userService;
    private final JwtUtil jwtUtil;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            if ("/user/login".equals(request.getRequestURI()) || "/user/join".equals(request.getRequestURI())) {
                doFilter(request, response, filterChain);
            } else {
                String token = parseJwt(request);
                if (token == null) {
                    response.sendError(403);    //accessDenied
                } else {
                    DecodedJWT tokenInfo = jwtUtil.decodeToken(token);
                    if (tokenInfo != null) {
                        String userId = tokenInfo.getClaim("userId").asString();
                        UserDetails loginUser = userService.loadUserByUsername(userId);
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                loginUser, null, loginUser.getAuthorities()
                        );

                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        //시큐리티 권한 부여
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        doFilter(request, response, filterChain);
                    } else {
                        log.error("### TokenInfo is Null");
                    }
                }
            }
        } catch (Exception e) {
            log.error("### Filter Exception {}", e.getMessage());
        }
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7, headerAuth.length());
        }
        return null;
    }
}
