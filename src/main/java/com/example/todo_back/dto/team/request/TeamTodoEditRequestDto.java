package com.example.todo_back.dto.team.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TeamTodoEditRequestDto {
    private String teamid;
    private String todoid;
    private String content;
}
