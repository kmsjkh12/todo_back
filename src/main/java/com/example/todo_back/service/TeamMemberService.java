package com.example.todo_back.service;

import com.example.todo_back.dto.team.request.TeamJoinRequestDto;
import com.example.todo_back.dto.team.response.TeamResponseDto;

import java.util.List;

public interface TeamMemberService {
    void joinTeam(Long userid,TeamJoinRequestDto teamJoinRequestDto);

    void joinTeam(Long userid,Long teamid);

    void existsById(Long userid, String teamid);


    List<Long> myTeam(Long userid);
}
