package com.example.todo_back.mapper;

import com.example.todo_back.dto.todo.response.TodoResponseDto;
import com.example.todo_back.entity.Todo;

import java.util.List;
import java.util.stream.Collectors;

public class TodoMapper {
    public static List<TodoResponseDto> mapToDtoList(List<Todo> todoList) {
        return todoList.stream()
                .map(todo -> TodoResponseDto.builder()
                        .id(todo.getId())
                        .content(todo.getContent())
                        .complete(todo.getComplete())
                        .createAt(todo.getCreateAt())
                        .build())
                .collect(Collectors.toList());
    }
}
