package com.example.todo_back.handler;

import com.example.todo_back.dto.user.UserPrincipalDetails;
import com.example.todo_back.dto.user.response.UserResponseDto;
import com.example.todo_back.entity.User;
import com.example.todo_back.exception.ResourceNotFoundException;
import com.example.todo_back.exception.UserNotFoundException;
import com.example.todo_back.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Component
public class CustomHandshakeHandler implements HandshakeInterceptor {

    private final UserService userService;

    public CustomHandshakeHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) throws Exception {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(request instanceof ServletServerHttpRequest){
            HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();
            String teamId = servletRequest.getParameter("teamid");
System.out.print("fuck : " + teamId);
            if( teamId != null && !teamId.isEmpty()){
                attributes.put("teamid", teamId);
            }
            else{
                throw new ResourceNotFoundException("해당되는 팀 대화가 없습니다");
            }
        }


        if (principal instanceof UserPrincipalDetails) {
            UserPrincipalDetails userDetails = (UserPrincipalDetails) principal;
            User user = userDetails.getUser();

            // WebSocket 세션에 사용자 정보 추가
            attributes.put("userid", String.valueOf(user.getId()));
            attributes.put("email", user.getEmail());
            attributes.put("nickname", user.getNickname());

            return true;
        } else {
            throw new UserNotFoundException("인증된 사용자가 없습니다. 로그인이 필요합니다.");
        }
    }



    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }
}
