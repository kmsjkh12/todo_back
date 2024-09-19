package com.example.todo_back.controller;

import com.example.todo_back.dto.*;
import com.example.todo_back.dto.todo.request.TodoAddRequestDto;
import com.example.todo_back.dto.todo.request.TodoCompleteRequestDto;
import com.example.todo_back.dto.todo.request.TodoEditRequestDto;
import com.example.todo_back.dto.todo.response.TodoGetResponseDto;
import com.example.todo_back.dto.todo.response.TodoResponseDto;
import com.example.todo_back.dto.user.UserPrincipalDetails;
import com.example.todo_back.service.TodoServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TodoController {

    private final TodoServiceImpl todoService;

    public TodoController(TodoServiceImpl todoService) {
        this.todoService = todoService;
    }

    //todo 전체 검색
    @GetMapping("/todos")
    public ResponseEntity<? super ResponseDto> getTodoAll(@AuthenticationPrincipal UserPrincipalDetails userPrincipalDetails) {

        List<TodoResponseDto> todoResponseDtoList = todoService.getTodoAll(userPrincipalDetails.getUser().getId());
        return TodoGetResponseDto.success(todoResponseDtoList);
    }


    //todo 날짜로 검색
    @GetMapping("/todos/date")
    public ResponseEntity<? super ResponseDto> getTodoDate(@AuthenticationPrincipal UserPrincipalDetails userPrincipalDetails, @RequestParam("date") String date) {
        List<TodoResponseDto> todoResponseDtoList = todoService.getTodoDate(userPrincipalDetails.getUser().getId(), date);
        return TodoGetResponseDto.success(todoResponseDtoList);
    }

    //todo 완료 여부로 검색
    @GetMapping("/todos/complete")
    public ResponseEntity<? super ResponseDto> getTodoComplete(@AuthenticationPrincipal UserPrincipalDetails userPrincipalDetails, @RequestParam("complete") String complete) {
        List<TodoResponseDto> todoResponseDtoList = todoService.getTodoComplete(userPrincipalDetails.getUser().getId(), complete);
        return TodoGetResponseDto.success(todoResponseDtoList);
    }

    //todo 생성
    //body userid, content
    @PostMapping("/todos")
    public ResponseEntity<? super ResponseDto> addTodos(@AuthenticationPrincipal UserPrincipalDetails userPrincipalDetails, @RequestBody TodoAddRequestDto todoAddRequestDto) {
        todoService.addTodo(userPrincipalDetails.getUser().getId(), todoAddRequestDto);
        return ResponseDto.success();
    }

    //todo 내용 수정
    @PatchMapping("/todos/edit")
    public ResponseEntity<? super ResponseDto> editTodoContent(@AuthenticationPrincipal UserPrincipalDetails userPrincipalDetails, @RequestBody TodoEditRequestDto todoEditRequestDto) {
        todoService.editTodoContent(userPrincipalDetails.getUser().getId(), todoEditRequestDto);
        return ResponseDto.success();
    }

    //todo 완료 토글
    @PatchMapping("/todos/complete")
    public ResponseEntity<? super ResponseDto> completeTodos(@AuthenticationPrincipal UserPrincipalDetails userPrincipalDetails, @RequestBody TodoCompleteRequestDto todoCompleteRequestDto) {
        todoService.completeTodo(userPrincipalDetails.getUser().getId(), todoCompleteRequestDto);
        return ResponseDto.success();
    }

    //todo 삭제
    @DeleteMapping("/todos")
    public ResponseEntity<? super ResponseDto> deleteTodos(@AuthenticationPrincipal UserPrincipalDetails userPrincipalDetails, @RequestParam("todoid") String todoid) {
        todoService.deleteTodo(userPrincipalDetails.getUser().getId(),todoid);
        return ResponseDto.success();
    }
}
