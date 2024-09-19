package com.example.todo_back.entity;

import com.example.todo_back.type.CompleteState;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name="TeamTodo")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamTodo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true,nullable = false)
    private Long id;

    @Column(name = "teamid",  nullable = false)
    private Long teamid;

    @Column(name = "author",  nullable = false)
    private Long author;

    @Column(name = "content",  nullable = false)
    private String content;

    @Column(name = "create_at", nullable = false)
    private LocalDate createAt;


    @Column(name = "complete",  nullable = false)
    @Enumerated(EnumType.STRING)
    private CompleteState complete;

    public void updateTodoContent (String content) {
        this.content = content;
    }
    public void completeTodo (CompleteState complete){
        this.complete=complete;
    }
}
