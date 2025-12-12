package org.example.taskservice.repository;

import org.example.taskservice.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findByBoardColumnIdOrderByPositionAsc(Long columnId);
}
