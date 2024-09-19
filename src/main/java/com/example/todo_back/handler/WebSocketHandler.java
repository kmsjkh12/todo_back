package com.example.todo_back.handler;

import com.example.todo_back.dto.Message;
import com.example.todo_back.exception.ResourceNotFoundException;
import com.example.todo_back.exception.UserNotFoundException;
import com.example.todo_back.util.Utils;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class WebSocketHandler extends TextWebSocketHandler {
    private final Map<Long, Map<String, WebSocketSession>> teamSessions = new ConcurrentHashMap<>();

    //연결하기
    @Override
    public void afterConnectionEstablished (WebSocketSession webSocketSession) throws IOException {
        String sessionId = (String) webSocketSession.getAttributes().get("userid");  //보내는 사람 ID
        String nickname = (String) webSocketSession.getAttributes().get("nickname"); //보내는 사람 NICKNAME
        String teamIdStr = (String) webSocketSession.getAttributes().get("teamid");  //보내는 사람의 TEAM ID

        Long teamId = Long.parseLong(teamIdStr);

        teamSessions.putIfAbsent(teamId, new ConcurrentHashMap<>());
        teamSessions.get(teamId).put(sessionId, webSocketSession);


        Message message = Message.builder()
                .sender(sessionId)
                .senderNickname(nickname) //보내는 사람 NICKNAME
                .teamid(teamId)
                .receiver("all")
                .build();
        message.newConnect();

        if (!teamSessions.containsKey(teamId)) {
            log.error("Team not found: teamId={}", teamId);
            System.out.print("errororororor");
            return;
        }


        teamSessions.get(teamId).values().forEach(s ->{
            //세션에서 본인 제외한 사람들에게 채팅 입장 메세지
            try{
                if(!s.getAttributes().get("userid").equals(sessionId)){
                    s.sendMessage(new TextMessage(Utils.getString(message)));
                }
            }
            catch (Exception e){
                throw new ResourceNotFoundException("팀이 연결할 수 없습니다.");
            }
        });


    }



    @Override
    public void handleTextMessage (WebSocketSession webSocketSession, TextMessage textMessage) throws Exception {
        String payload = textMessage.getPayload();  //웹에서 보내는 message
        String sender = (String) webSocketSession.getAttributes().get("userid"); //보내는 사람 ID
        String nickname = (String) webSocketSession.getAttributes().get("nickname"); //보내는 사람 NICKNAME
        String teamIdStr = (String) webSocketSession.getAttributes().get("teamid");  //보내는 사람의 TEAM ID

        Long teamId = Long.parseLong(teamIdStr);

        String payloadJson = "{\"sender\":\"" + sender + "\",\"data\":\"" + payload + "\",\"senderNickname\":\"" + nickname + "\",\"teamId\":" + teamId + "}";
        if(teamSessions.isEmpty()){
            System.out.println("??");
        }
        //전송
        teamSessions.get(teamId).values().forEach(s-> {
            try {
                s.sendMessage(new TextMessage(payloadJson));
            } catch (IOException e) {
                throw new ResourceNotFoundException("팀이 존재하지 않습니다.");
            }
        });
    }

    //종료
    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) {
        String sessionId = (String) webSocketSession.getAttributes().get("userid");
        String teamIdStr = (String) webSocketSession.getAttributes().get("teamid");
        Long teamId = Long.parseLong(teamIdStr);

        if (teamSessions.containsKey(teamId)) {
            teamSessions.get(teamId).remove(sessionId);
            if (teamSessions.get(teamId).isEmpty()) {
                teamSessions.remove(teamId);
            }
        }

        log.info("Connection closed: sessionId={}, teamId={}", sessionId, teamId);
    }


    //에러
    @Override
    public void handleTransportError (WebSocketSession webSocketSession, Throwable throwable){
    }
}
