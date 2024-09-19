package com.example.todo_back.service;

import com.example.todo_back.dto.user.request.UserJoinRequestDto;
import com.example.todo_back.dto.user.response.UserResponseDto;

public interface UserService {

    void existsById(Long userid);

    UserResponseDto findById(Long userid);

    UserResponseDto login(String email, String password);

    void join(UserJoinRequestDto userJoinRequestDto);
}
