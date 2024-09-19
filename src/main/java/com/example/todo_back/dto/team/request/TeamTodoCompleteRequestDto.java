package com.example.todo_back.dto.team.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class TeamTodoCompleteRequestDto {
    private String teamid;
    private String todoid;
}
