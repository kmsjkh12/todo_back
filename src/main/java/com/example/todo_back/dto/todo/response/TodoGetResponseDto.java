package com.example.todo_back.dto.todo.response;

import com.example.todo_back.dto.ResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
@NoArgsConstructor
public class TodoGetResponseDto extends ResponseDto {

    private List<TodoResponseDto> todoResponseDto;

    public TodoGetResponseDto(List<TodoResponseDto> todoResponseDto){
        super();
        this.todoResponseDto=todoResponseDto;
    }

    public static ResponseEntity<ResponseDto> success(List<TodoResponseDto> todoResponseDto) {
        ResponseDto responseBody = new TodoGetResponseDto(todoResponseDto);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}
