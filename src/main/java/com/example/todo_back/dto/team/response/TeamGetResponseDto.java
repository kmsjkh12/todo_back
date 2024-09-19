package com.example.todo_back.dto.team.response;

import com.example.todo_back.dto.ResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
@NoArgsConstructor
public class TeamGetResponseDto extends ResponseDto {
    List<TeamResponseDto> teamTodoResponseDtoList;

    public TeamGetResponseDto(List<TeamResponseDto> teamTodoResponseDtoList){
        super();
        this.teamTodoResponseDtoList=teamTodoResponseDtoList;
    }

    public static ResponseEntity<ResponseDto> success(List<TeamResponseDto> teamTodoResponseDtoList) {
        ResponseDto responseBody = new TeamGetResponseDto(teamTodoResponseDtoList);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}
