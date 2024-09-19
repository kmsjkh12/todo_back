package com.example.todo_back.controller;

import com.example.todo_back.dto.ResponseDto;
import com.example.todo_back.dto.team.request.TeamJoinRequestDto;
import com.example.todo_back.dto.team.response.TeamGetResponseDto;
import com.example.todo_back.dto.team.response.TeamResponseDto;
import com.example.todo_back.dto.user.UserPrincipalDetails;
import com.example.todo_back.service.TeamMemberService;
import com.example.todo_back.service.TeamService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TeamMemberController {
    private final TeamMemberService teamMemberService;
    private final TeamService teamService;

    public TeamMemberController(TeamMemberService teamMemberService, TeamService teamService) {
        this.teamMemberService = teamMemberService;
        this.teamService = teamService;
    }

    @PostMapping("/team/add")
    public ResponseEntity<? super ResponseDto> registerTeam (@AuthenticationPrincipal UserPrincipalDetails userPrincipalDetails, @RequestBody TeamJoinRequestDto teamJoinRequestDto){
        teamMemberService.joinTeam(userPrincipalDetails.getUser().getId(), teamJoinRequestDto);
        return ResponseDto.success();
    }

    @GetMapping("/team/my")
    public ResponseEntity<? super TeamGetResponseDto> myTeam (@AuthenticationPrincipal UserPrincipalDetails userPrincipalDetails){
        List<Long> teamids  = teamMemberService.myTeam(userPrincipalDetails.getUser().getId());
        List<TeamResponseDto> teamResponseDtos = teamService.getTeam(teamids);
        return TeamGetResponseDto.success(teamResponseDtos);
    }
}




//팀 가입 요청
//팀 id, userid