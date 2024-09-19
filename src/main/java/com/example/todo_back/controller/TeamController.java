package com.example.todo_back.controller;

import com.example.todo_back.dto.ResponseDto;
import com.example.todo_back.dto.team.request.TeamCreateRequestDto;
import com.example.todo_back.dto.user.UserPrincipalDetails;
import com.example.todo_back.service.TeamService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TeamController {

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @PostMapping("/team")
    public ResponseEntity<? super ResponseDto> joinTeam (@AuthenticationPrincipal UserPrincipalDetails userPrincipalDetails, @RequestBody TeamCreateRequestDto teamCreateRequestDto){
        teamService.createTeam(userPrincipalDetails.getUser().getId(), teamCreateRequestDto);

        return ResponseDto.success();
    }




}
