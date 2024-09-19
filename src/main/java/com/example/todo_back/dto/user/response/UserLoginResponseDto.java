package com.example.todo_back.dto.user.response;

import com.example.todo_back.dto.ResponseDto;
import com.example.todo_back.dto.team.response.TeamTodoGetResponseDto;
import com.example.todo_back.dto.team.response.TeamTodoResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
@NoArgsConstructor
public class UserLoginResponseDto extends ResponseDto {

    UserResponseDto userResponseDto;
    public UserLoginResponseDto(UserResponseDto userResponseDto){
        super();
        this.userResponseDto=userResponseDto;
    }

    public static ResponseEntity<ResponseDto> success(UserResponseDto userResponseDto) {
        ResponseDto responseBody = new UserLoginResponseDto(userResponseDto);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}
