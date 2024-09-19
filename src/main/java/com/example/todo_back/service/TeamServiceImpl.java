package com.example.todo_back.service;

import com.example.todo_back.dto.team.request.TeamCreateRequestDto;
import com.example.todo_back.dto.team.response.TeamResponseDto;
import com.example.todo_back.entity.Team;
import com.example.todo_back.entity.TeamMember;
import com.example.todo_back.exception.ResourceNotFoundException;
import com.example.todo_back.repository.TeamMemberRepository;
import com.example.todo_back.repository.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeamServiceImpl implements TeamService{

    private final TeamRepository teamRepository;
    private final UserService userService;
    private final TeamMemberService teamMemberService;
    public TeamServiceImpl(TeamRepository teamRepository, UserService userService, TeamMemberService teamMemberService) {
        this.teamRepository = teamRepository;
        this.userService = userService;
        this.teamMemberService = teamMemberService;
    }


    @Override
    public void createTeam(Long userid,TeamCreateRequestDto teamCreateRequestDto) {

        userService.existsById(userid);

        Team team = Team.builder()
                .teamName(teamCreateRequestDto.getTeamName())
                .teamLeader(userid)
                .build();

        teamRepository.save(team);

        teamMemberService.joinTeam(team.getTeamLeader(),team.getId());
    }

    @Override
    public void existsById(Long teamid) {
        teamRepository.findById(teamid).orElseThrow(() -> new ResourceNotFoundException("해당되는 팀은 없습니다."));
    }



    @Override
    public List<TeamResponseDto> getTeam(List<Long> teamid) {
        List<Team> teams= teamRepository.findAllById(teamid);

        return teams.stream().map(t ->
            TeamResponseDto.builder()
                    .teamid(t.getId())
                    .teamname(t.getTeamName()).build()
        ).collect(Collectors.toList());
    }


}
