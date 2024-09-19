package com.example.todo_back.controller;

import com.example.todo_back.dto.ResponseDto;
import com.example.todo_back.dto.user.request.LoginRequestDto;
import com.example.todo_back.dto.user.request.UserJoinRequestDto;
import com.example.todo_back.dto.user.response.UserLoginResponseDto;
import com.example.todo_back.dto.user.response.UserResponseDto;
import com.example.todo_back.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user/login")
    public ResponseEntity<? super UserLoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto){

        UserResponseDto user= userService.login(loginRequestDto.getUsername(), loginRequestDto.getPassword());

        return UserLoginResponseDto.success(user);
    }

    @PostMapping("/user/join")
    public ResponseEntity<? super ResponseDto> join(@RequestBody UserJoinRequestDto userJoinRequestDto){
        userService.join(userJoinRequestDto);

        return ResponseDto.success();
    }

}
