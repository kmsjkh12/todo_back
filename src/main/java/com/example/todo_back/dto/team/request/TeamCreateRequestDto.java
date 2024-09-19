package com.example.todo_back.dto.team.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TeamCreateRequestDto {
    @JsonProperty("teamName")
    private String teamName;
}
