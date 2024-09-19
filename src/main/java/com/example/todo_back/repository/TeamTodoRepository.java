package com.example.todo_back.repository;

import com.example.todo_back.entity.TeamTodo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TeamTodoRepository extends JpaRepository<TeamTodo, Long> {
    List<TeamTodo> findByTeamid(Long teamid);

}
