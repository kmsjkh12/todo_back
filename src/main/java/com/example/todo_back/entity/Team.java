package com.example.todo_back.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name="Team")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true,nullable = false)
    private Long id;

    @Column(name = "teamname",  nullable = false)
    private String teamName;

    @Column(name = "teamleader",  nullable = false)
    private Long teamLeader;




}
