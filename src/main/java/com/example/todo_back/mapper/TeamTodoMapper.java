package com.example.todo_back.mapper;

import com.example.todo_back.dto.team.response.TeamTodoResponseDto;
import com.example.todo_back.dto.todo.response.TodoResponseDto;
import com.example.todo_back.entity.TeamTodo;
import com.example.todo_back.entity.Todo;

import java.util.List;
import java.util.stream.Collectors;

public class TeamTodoMapper {
    public static List<TeamTodoResponseDto> mapToDtoList(List<TeamTodo> teamTodoList) {
        return teamTodoList.stream()
                .map(todo -> TeamTodoResponseDto.builder()
                        .id(todo.getId())
                        .teamId(todo.getTeamid())
                        .complete(todo.getComplete())
                        .content(todo.getContent())
                        .createAt(todo.getCreateAt())
                        .userid(todo.getAuthor())
                        .build())
                .collect(Collectors.toList());
    }
}
