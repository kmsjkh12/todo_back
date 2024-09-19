package com.example.todo_back.service;

import com.example.todo_back.dto.team.request.TeamJoinRequestDto;
import com.example.todo_back.dto.team.response.TeamResponseDto;
import com.example.todo_back.entity.TeamMember;
import com.example.todo_back.exception.ResourceNotFoundException;
import com.example.todo_back.repository.TeamMemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeamMemberServiceImpl implements TeamMemberService {

    private final TeamMemberRepository teamMemberRepository;
    private final UserService userService;

    public TeamMemberServiceImpl(TeamMemberRepository teamMemberRepository, UserService userService) {
        this.teamMemberRepository = teamMemberRepository;
        this.userService = userService;
    }

    //팀원 가입 시 로직
    @Override
    public void joinTeam(Long userid, TeamJoinRequestDto teamJoinRequestDto) {
        userService.existsById(userid);

        TeamMember teamMember = TeamMember.builder()
                .team(Long.parseLong(teamJoinRequestDto.getTeamid()))
                .userid(userid)
                .build();

        teamMemberRepository.save(teamMember);
    }

    //리더 생성시 로직
    @Override
    public void joinTeam(Long userid,Long teamid) {
        userService.existsById(userid);

        TeamMember teamMember= TeamMember.builder()
                .team(teamid)
                .userid(userid)
                .build();

        teamMemberRepository.save(teamMember);
    }

    @Override
    public void existsById(Long userid, String teamid) {
        teamMemberRepository.findByUseridAndTeam(userid, Long.parseLong(teamid)).orElseThrow(()-> new ResourceNotFoundException("해당되는 유저는 팀의 소속이 아닙니다."));
    }

    @Override
    public List<Long> myTeam(Long userid) {
        List<TeamMember> teamMembers = teamMemberRepository.findByUserid(userid);

        return teamMembers.stream().map(
                teamMember -> teamMember.getTeam()
        ).collect(Collectors.toList());
    }
}
