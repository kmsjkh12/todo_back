package com.example.todo_back.filter;

import com.example.todo_back.config.JwtUtil;
import com.example.todo_back.dto.user.UserPrincipalDetails;
import com.example.todo_back.entity.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.naming.AuthenticationException;
import java.io.IOException;

public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String test_path = request.getRequestURI();
        String token = null;

        if(test_path.startsWith("/test") ||
                test_path.startsWith("/user/login") ||
                test_path.startsWith("/user/join") ||
                test_path.startsWith("/swagger-ui") ||
                test_path.startsWith("/v3/api-docs") ||
                test_path.startsWith("/swagger-resources") ||
                test_path.startsWith("/configuration/ui") ||
                test_path.startsWith("/configuration/security") ||
                test_path.startsWith("/webjars/")) {
            filterChain.doFilter(request, response);
            return;
        }

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("jwt")) {
                    token = cookie.getValue();

                }
            }
        }

        try {
            if (token != null && !jwtUtil.validateToken(token)) {
                throw new AuthenticationException("토큰이 일치하지 않습니다.");
            }


            String email = jwtUtil.getUserEmail(token);
            Long userid = jwtUtil.getUserId(token);
            String nickname = jwtUtil.getUserNickname(token);

            User user = User.builder()
                    .id(userid)
                    .email(email)
                    .nickname(nickname)
                    .build();

            UserPrincipalDetails userPrincipalDetails = new UserPrincipalDetails(user);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userPrincipalDetails, null, userPrincipalDetails.getAuthorities());

            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            filterChain.doFilter(request,response);
            //컨텍스트에 저장
        } catch (AuthenticationException e) {
            throw new RuntimeException(e);
        }



    }
}
