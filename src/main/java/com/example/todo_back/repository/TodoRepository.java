package com.example.todo_back.repository;


import com.example.todo_back.entity.Todo;
import com.example.todo_back.type.CompleteState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findByUserid(Long userid);
    List<Todo> findByCreateAtAndUserid(LocalDate date, Long userid);
    List<Todo> findByCompleteAndUserid( CompleteState completeState,Long userid);
}
