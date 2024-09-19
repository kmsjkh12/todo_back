package com.example.todo_back.dto.todo.response;

import com.example.todo_back.type.CompleteState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TodoResponseDto {
    private Long id;

    private String content;

    private CompleteState complete;

    private LocalDate createAt;
}
