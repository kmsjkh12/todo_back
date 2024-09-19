package com.example.todo_back.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    private String type;
    private String sender;
    private String senderNickname;
    private String receiver;
    private Object data;
    private Long teamid;

    public void setSender(String sender){this.sender=sender;}

    public void newConnect(){
        this.type="new";
    }
    public void closeConnect(){
        this.type="close";
    }
}
