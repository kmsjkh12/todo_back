package com.example.todo_back.dto.team.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeamResponseDto {

    private Long teamid;
    private String teamname;
}
