package com.example.todo_back.dto.team.response;

import com.example.todo_back.dto.ResponseDto;
import com.example.todo_back.dto.todo.response.TodoGetResponseDto;
import com.example.todo_back.dto.todo.response.TodoResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
@NoArgsConstructor
public class TeamTodoGetResponseDto extends ResponseDto {
    List<TeamTodoResponseDto> teamTodoResponseDtoList;

    public TeamTodoGetResponseDto( List<TeamTodoResponseDto> teamTodoResponseDtoList){
        super();
        this.teamTodoResponseDtoList=teamTodoResponseDtoList;
    }

    public static ResponseEntity<ResponseDto> success( List<TeamTodoResponseDto> teamTodoResponseDtoList) {
        ResponseDto responseBody = new TeamTodoGetResponseDto(teamTodoResponseDtoList);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}
