package com.example.todo_back.config;


import com.example.todo_back.handler.CustomHandshakeHandler;
import com.example.todo_back.handler.WebSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
//웹 소켓 활성화
public class WebSocketConfig implements WebSocketConfigurer {
    private final CustomHandshakeHandler customHandshakeHandler;

    public WebSocketConfig(CustomHandshakeHandler customHandshakeHandler) {
        this.customHandshakeHandler = customHandshakeHandler;
    }

    //팀 투두 채팅 기능
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler(), "/room")
                .setAllowedOriginPatterns("*").addInterceptors(customHandshakeHandler);
        registry.addHandler(webSocketHandler(), "/room")
                .setAllowedOriginPatterns("*").withSockJS().setInterceptors(customHandshakeHandler);
    }
    @Bean
    public WebSocketHandler webSocketHandler(){
        return new WebSocketHandler();

    }



}
