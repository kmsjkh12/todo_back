package com.example.todo_back.dto.todo.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TodoEditRequestDto {
    @JsonProperty("todoid")
    private String todoid;

    @JsonProperty("content")
    private String content;


}

