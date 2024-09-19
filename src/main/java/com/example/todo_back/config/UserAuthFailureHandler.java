package com.example.todo_back.config;

import com.example.todo_back.dto.ResponseDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException; // Correct import for Spring Security
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import java.io.IOException;

public class UserAuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest req, HttpServletResponse rep, AuthenticationException exception)
            throws ServletException, IOException {


        // 실패 시 userNotFound 응답 리턴
        ResponseEntity<ResponseDto> response = ResponseDto.userNotFound("Authentication failed: " + exception.getMessage());
        rep.setStatus(response.getStatusCodeValue());
        rep.setContentType("application/json");
        rep.getWriter().write("{\"code\": \"" + response.getBody().getCode() + "\", \"message\": \"" + response.getBody().getMessage() + "\"}");

        //실패 메세지 추가
    }
}
