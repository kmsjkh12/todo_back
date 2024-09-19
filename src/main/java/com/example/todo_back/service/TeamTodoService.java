package com.example.todo_back.service;

import com.example.todo_back.dto.team.request.*;
import com.example.todo_back.dto.team.response.TeamTodoResponseDto;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface TeamTodoService {
    List<TeamTodoResponseDto> getTeamTodo (Long userid, String teamid);

    void addTeamTodo(Long userid, TeamTodoAddRequestDto teamTodoAddRequestDto);
    void editTeamTodo(Long userid, TeamTodoEditRequestDto teamTodoEditRequestDto);
    void completeTeamTodo(Long userid, TeamTodoCompleteRequestDto teamTodoCompleteRequestDto);
    void deleteTeamTodo(Long userid,TeamTodoCompleteRequestDto teamTodoCompleteRequestDto );
}
