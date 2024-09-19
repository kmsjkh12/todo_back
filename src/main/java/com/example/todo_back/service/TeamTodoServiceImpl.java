package com.example.todo_back.service;

import com.example.todo_back.dto.team.request.TeamJoinRequestDto;
import com.example.todo_back.dto.team.request.TeamTodoAddRequestDto;
import com.example.todo_back.dto.team.request.TeamTodoCompleteRequestDto;
import com.example.todo_back.dto.team.request.TeamTodoEditRequestDto;
import com.example.todo_back.dto.team.response.TeamTodoResponseDto;
import com.example.todo_back.entity.Team;
import com.example.todo_back.entity.TeamTodo;
import com.example.todo_back.entity.Todo;
import com.example.todo_back.exception.ResourceNotFoundException;
import com.example.todo_back.mapper.TeamTodoMapper;
import com.example.todo_back.repository.TeamTodoRepository;
import com.example.todo_back.type.CompleteState;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class TeamTodoServiceImpl implements TeamTodoService{

    private final TeamTodoRepository teamTodoRepository;
    private final UserService userService;
    private final TeamMemberService teamMemberService;


    public TeamTodoServiceImpl(TeamTodoRepository teamTodoRepository, UserService userService, TeamMemberService teamMemberService) {
        this.teamTodoRepository = teamTodoRepository;
        this.userService = userService;
        this.teamMemberService = teamMemberService;
    }

    public List<TeamTodoResponseDto> getTeamTodo (Long userid, String teamid){
        userService.existsById(userid);

        teamMemberService.existsById(userid, teamid);

        List<TeamTodo> teamTodoList = teamTodoRepository.findByTeamid(Long.parseLong(teamid));

        return TeamTodoMapper.mapToDtoList(teamTodoList);
    }

    @Override
    public void addTeamTodo(Long userid,TeamTodoAddRequestDto teamTodoAddRequestDto) {
        userService.existsById(userid);

        teamMemberService.existsById(userid, teamTodoAddRequestDto.getTeamid());

        TeamTodo teamTodo = TeamTodo.builder()
                .author(userid)
                .content(teamTodoAddRequestDto.getContent())
                .createAt(LocalDate.now())
                .teamid(Long.parseLong(teamTodoAddRequestDto.getTeamid()))
                .complete(CompleteState.INCOMPLETE)
                .build();

        teamTodoRepository.save(teamTodo);

    }

    @Transactional
    @Override
    public void editTeamTodo(Long userid, TeamTodoEditRequestDto teamTodoEditRequestDto) {
        userService.existsById(userid);

        TeamTodo teamTodo = teamTodoRepository.findById(Long.valueOf(teamTodoEditRequestDto.getTodoid())).orElseThrow(() -> new ResourceNotFoundException("존재하는 todo가 없습니다"));

        //재차 검증
        if(!teamTodo.getTeamid().equals(teamTodoEditRequestDto.getTeamid())){
            new ResourceNotFoundException("일치하지 않는 팀입니다");
        }
        teamTodo.updateTodoContent(teamTodoEditRequestDto.getContent());
    }

    @Transactional
    @Override
    public void completeTeamTodo(Long userid, TeamTodoCompleteRequestDto teamTodoCompleteRequestDto) {
        userService.existsById(userid);

        TeamTodo teamTodo = teamTodoRepository.findById(Long.valueOf(teamTodoCompleteRequestDto.getTodoid())).orElseThrow(() -> new ResourceNotFoundException("존재하는 todo가 없습니다"));

        //재차 검증
        if(!teamTodo.getTeamid().equals(teamTodoCompleteRequestDto.getTeamid())){
            new ResourceNotFoundException("일치하지 않는 팀입니다");
        }
        teamTodo.completeTodo(teamTodo.getComplete().toggle());
    }


    @Transactional
    @Override
    public void deleteTeamTodo(Long userid, TeamTodoCompleteRequestDto teamTodoCompleteRequestDto) {
        userService.existsById(userid);

        teamTodoRepository.deleteById(Long.parseLong(teamTodoCompleteRequestDto.getTodoid()));
    }
}
