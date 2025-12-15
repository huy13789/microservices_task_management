package org.example.taskservice.repository;

import org.example.taskservice.model.CardAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardAssignmentRepository extends JpaRepository<CardAssignment, Long> {
}
