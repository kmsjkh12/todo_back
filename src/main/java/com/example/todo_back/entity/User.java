package com.example.todo_back.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name="User")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true,nullable = false)
    private Long id;

    @Column(name = "email", unique = true,  nullable = false)
    private String email;

    @Column(name = "password",  nullable = false)
    private String password;

    @Column(name = "nickname", nullable = false)
    private String nickname;
}
