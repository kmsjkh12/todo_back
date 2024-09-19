package com.example.todo_back.controller;

import com.example.todo_back.dto.ResponseDto;
import com.example.todo_back.dto.team.request.TeamJoinRequestDto;
import com.example.todo_back.dto.team.request.TeamTodoAddRequestDto;
import com.example.todo_back.dto.team.request.TeamTodoCompleteRequestDto;
import com.example.todo_back.dto.team.request.TeamTodoEditRequestDto;
import com.example.todo_back.dto.team.response.TeamTodoGetResponseDto;
import com.example.todo_back.dto.team.response.TeamTodoResponseDto;
import com.example.todo_back.dto.user.UserPrincipalDetails;
import com.example.todo_back.service.TeamTodoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TeamTodoController {

    private final TeamTodoService teamTodoService;

    public TeamTodoController(TeamTodoService teamTodoService) {
        this.teamTodoService = teamTodoService;
    }

    @GetMapping("/team/todo")
    public ResponseEntity<? super TeamTodoGetResponseDto> getTeamTodo (@AuthenticationPrincipal UserPrincipalDetails userPrincipalDetails,@RequestParam("teamid") String teamid) {

        List<TeamTodoResponseDto> teamTodoResponseDtoList = teamTodoService.getTeamTodo(userPrincipalDetails.getUser().getId(),teamid);

        return TeamTodoGetResponseDto.success(teamTodoResponseDtoList);
    }

    @PostMapping("/team/todo")
    public ResponseEntity<? super ResponseDto> addTeamTodo (@AuthenticationPrincipal UserPrincipalDetails userPrincipalDetails,@RequestBody TeamTodoAddRequestDto teamTodoAddRequestDto) {
        teamTodoService.addTeamTodo(userPrincipalDetails.getUser().getId(),teamTodoAddRequestDto);
        return ResponseDto.success();
    }

    @PatchMapping("/team/todo/edit")
    public ResponseEntity<? super ResponseDto> editTeamTodoCotent (@AuthenticationPrincipal UserPrincipalDetails userPrincipalDetails,@RequestBody TeamTodoEditRequestDto TeamTodoEditRequestDto) {
        teamTodoService.editTeamTodo(userPrincipalDetails.getUser().getId(),TeamTodoEditRequestDto);
        return ResponseDto.success();
    }

    @PatchMapping("/team/todo/complete")
    public ResponseEntity<? super ResponseDto> editTeamTodoComplete (@AuthenticationPrincipal UserPrincipalDetails userPrincipalDetails,@RequestBody TeamTodoCompleteRequestDto teamTodoCompleteRequestDto) {
        teamTodoService.completeTeamTodo(userPrincipalDetails.getUser().getId(),teamTodoCompleteRequestDto);

        return ResponseDto.success();
    }

    @DeleteMapping("/team/todos")
    public ResponseEntity<? super ResponseDto> deleteTodos(@AuthenticationPrincipal UserPrincipalDetails userPrincipalDetails,@ModelAttribute TeamTodoCompleteRequestDto teamTodoCompleteRequestDto) {
        System.out.println("hello"+ teamTodoCompleteRequestDto.getTodoid() + " " + teamTodoCompleteRequestDto.getTeamid());
        teamTodoService.deleteTeamTodo(userPrincipalDetails.getUser().getId(),teamTodoCompleteRequestDto);

        return ResponseDto.success();
    }
}
