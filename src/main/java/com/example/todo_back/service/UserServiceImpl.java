package com.example.todo_back.service;

import com.example.todo_back.dto.user.request.UserJoinRequestDto;
import com.example.todo_back.dto.user.response.UserResponseDto;
import com.example.todo_back.entity.User;
import com.example.todo_back.exception.UserNotFoundException;
import com.example.todo_back.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public void existsById(Long userid){
        System.out.println("useriddd: userid" + userid);
        userRepository.findById(userid).orElseThrow(() -> new UserNotFoundException("유저가 존재하지 않습니다."));
    }

    @Override
    public UserResponseDto findById(Long userid){

        User user = userRepository.findById(userid).orElseThrow(() -> new UserNotFoundException("유저가 존재하지 않습니다."));

        return new UserResponseDto(user.getId(), user.getEmail(), user.getNickname());
    }

    @Override
    public UserResponseDto login(String email, String password) {

        User user = userRepository.findByEmailAndPassword(email, password).orElseThrow(() -> new UserNotFoundException("유저가 존재하지 않습니다."));
        return new UserResponseDto(user.getId(), user.getEmail(), user.getNickname());
    }

    @Override
    public void join(UserJoinRequestDto userJoinRequestDto) {

        userRepository.findByEmail(userJoinRequestDto.getUseremail())
                .ifPresent(user -> {
                    throw new UserNotFoundException("이미 존재하는 이메일입니다");
                });
        User user = User.builder()
                .email(userJoinRequestDto.getUseremail())
                .password(passwordEncoder.encode(userJoinRequestDto.getPassword()))
                .nickname(userJoinRequestDto.getNickname())
                .build();

        userRepository.save(user);
    }
}
