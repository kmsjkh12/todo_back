package com.example.todo_back.dto.team.response;


import com.example.todo_back.type.CompleteState;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeamTodoResponseDto {
    private Long id;
    private Long teamId;
    private Long userid;
    private String content;
    private LocalDate createAt;
    private CompleteState complete;
}
