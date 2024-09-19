package com.example.todo_back.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="TeamMember")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true,nullable = false)
    private Long id;

    @Column(name = "team",  nullable = false)
    private Long team;

    @Column(name = "userid",  nullable = false)
    private Long userid;

}
