package com.example.todo_back.dto.user.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserJoinRequestDto {
    private String useremail;
    private String password;
    private String nickname;
}
