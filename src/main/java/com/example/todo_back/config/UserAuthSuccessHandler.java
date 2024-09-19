package com.example.todo_back.config;

import com.example.todo_back.dto.ResponseDto;
import com.example.todo_back.entity.User;
import com.example.todo_back.exception.UserNotFoundException;
import com.example.todo_back.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import java.io.IOException;

public class UserAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;


    public UserAuthSuccessHandler(JwtUtil jwtUtil, UserRepository userRepository) {
        this.jwtUtil=jwtUtil;
        this.userRepository = userRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse rep, Authentication authentication)
            throws ServletException, IOException {

        Object principal = authentication.getPrincipal();
        //인증 객체에서 사용자의 정보를 추출하여 username 추출 (email)
        String username = (principal instanceof UserDetails) ? ((UserDetails) principal).getUsername() : principal.toString();

        User user= userRepository.findByEmail(username).orElseThrow(()->new UserNotFoundException("NOT USER"));

        //email로 user 가져오기


        String token = jwtUtil.createAccessToken(user);
        //토큰 생성

        Cookie cookie = new Cookie("jwt", token);
        cookie.setPath("/");
        cookie.setHttpOnly(false);  //true
        cookie.setSecure(false);
        cookie.setMaxAge(60 * 60);
        rep.setHeader("Set-Cookie", cookie.getName() + "=" + cookie.getValue() + "; HttpOnly; Secure=" + cookie.getSecure() + "; SameSite=None; Path=/");
        rep.addCookie(cookie);
        //헤더에 쿠키 추가

        ResponseEntity<ResponseDto> response = ResponseDto.success();
        rep.setStatus(response.getStatusCodeValue());
        rep.setContentType("application/json");
        rep.getWriter().write("{\"code\": \"" + response.getBody().getCode() + "\", \"message\": \"" + response.getBody().getMessage() + "\"}");
        //로그인 성공 메세지 추가
    }
}
