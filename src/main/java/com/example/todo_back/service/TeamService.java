package com.example.todo_back.service;

import com.example.todo_back.dto.team.request.TeamCreateRequestDto;
import com.example.todo_back.dto.team.response.TeamResponseDto;
import com.example.todo_back.entity.Team;

import java.util.List;

public interface TeamService {
    void createTeam(Long userid, TeamCreateRequestDto teamCreateRequestDto);
    void existsById(Long teamid);
    List<TeamResponseDto> getTeam(List<Long> teamid);

}
