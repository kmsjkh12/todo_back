package com.example.todo_back.repository;

import com.example.todo_back.entity.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {
    Optional<TeamMember> findByUseridAndTeam(Long userid, Long team);
    List<TeamMember> findByUserid(Long userid);
}
