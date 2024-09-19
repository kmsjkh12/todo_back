package com.example.todo_back.config;

import com.example.todo_back.dto.user.UserPrincipalDetails;
import com.example.todo_back.entity.User;
import com.example.todo_back.exception.UserNotFoundException;
import com.example.todo_back.service.UserPrincipalDetailService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserAuthenticationProvider implements AuthenticationProvider {
    private final UserPrincipalDetailService principalDetailService;

    public UserAuthenticationProvider(UserPrincipalDetailService principalDetailService) {
        this.principalDetailService = principalDetailService;
    }

    //  userDetail 객체로 인증 시도
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String userEmail = authentication.getName();
        String password = (String) authentication.getCredentials();

        //사용자 정보 저장
        UserPrincipalDetails userPrincipalDetails = (UserPrincipalDetails) principalDetailService.loadUserByUsername(userEmail);

        //암호화된 비밀번호
        String dbPassword = userPrincipalDetails.getPassword();


        //테스트를 위해 test1@test.com 에게만 비밀번호 인증 해제
        if(!userEmail.equals("test1@test.com")){
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            if(!passwordEncoder.matches(password, dbPassword)){
                System.out.print("비밀번호가 일치하지 않아요");
                throw new UserNotFoundException("올바른 아이디와 비밀번호가 아닙니다");
            }
        }
        else{
            if(!password.equals(dbPassword)){
                System.out.print("비밀번호가 일치하지 않아요");
                throw new UserNotFoundException("올바른 아이디와 비밀번호가 아닙니다");
            }
        }

        //user 정보 추출
        User user = userPrincipalDetails.getUser();

        if( user == null ){
            System.out.print("없는 계정입니다");
            throw new UserNotFoundException("없는 계정입니다");
        }


        return new UsernamePasswordAuthenticationToken(userPrincipalDetails,null,userPrincipalDetails.getAuthorities());
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
