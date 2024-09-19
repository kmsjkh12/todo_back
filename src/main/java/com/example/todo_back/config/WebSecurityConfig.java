package com.example.todo_back.config;

import com.example.todo_back.filter.JwtFilter;
import com.example.todo_back.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final UserAuthenticationProvider userAuthenticationProvider;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    //비밀번호 암호화
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //로그인 성공시 핸들러
    @Bean
    public UserAuthSuccessHandler userAuthSuccessHandler() {
        return new UserAuthSuccessHandler(jwtUtil,userRepository); // Register as a bean
    }

    //로그인 실패 핸들러
    @Bean
    public UserAuthFailureHandler userAuthFailureHandler() {
        return new UserAuthFailureHandler(); // Register as a bean
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf->csrf.disable());
        http
                .authenticationProvider(userAuthenticationProvider) //인증용 객체를 전달받는다
                .authorizeHttpRequests((authz) -> authz
                        .requestMatchers("/user/login", "/user/join","/swagger-ui/**", "/v3/api-docs/**").permitAll() // 접근 가능
                        .anyRequest().authenticated() // Protect other routes
                )
                .formLogin((form) -> form
                        .loginProcessingUrl("/user/login") // The URL to submit the login form
                        .successHandler(userAuthSuccessHandler())
                        .failureHandler(userAuthFailureHandler())
                        .permitAll()
                )
                .addFilterBefore(new JwtFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


}
