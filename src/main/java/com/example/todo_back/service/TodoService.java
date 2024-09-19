package com.example.todo_back.service;

import com.example.todo_back.dto.todo.request.TodoAddRequestDto;
import com.example.todo_back.dto.todo.request.TodoCompleteRequestDto;
import com.example.todo_back.dto.todo.request.TodoEditRequestDto;
import com.example.todo_back.dto.todo.response.TodoResponseDto;

import java.util.List;

public interface TodoService {
    List<TodoResponseDto> getTodoAll(Long userid);
    List<TodoResponseDto> getTodoDate(Long userid, String date);
    List<TodoResponseDto> getTodoComplete(Long userid, String complete);
    void addTodo (Long userid, TodoAddRequestDto todoAddRequestDto);
    void editTodoContent(Long userid, TodoEditRequestDto todoEditRequestDto);
    void completeTodo(Long userid, TodoCompleteRequestDto todoCompleteRequestDto);

    void deleteTodo(Long userid, String todoid);
}
