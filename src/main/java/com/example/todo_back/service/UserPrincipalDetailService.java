package com.example.todo_back.service;

import com.example.todo_back.dto.user.UserPrincipalDetails;
import com.example.todo_back.entity.User;
import com.example.todo_back.exception.UserNotFoundException;
import com.example.todo_back.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//로그인 요청 가로채기
@Service
public class UserPrincipalDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserPrincipalDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    //
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("유저가 존재하지 않습니다."));


        //userdetail을 상속받은 객체를 리턴한다.
        return new UserPrincipalDetails(user);
    }
}
