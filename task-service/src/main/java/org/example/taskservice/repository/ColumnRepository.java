package org.example.taskservice.repository;

import org.example.taskservice.model.BoardColumn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ColumnRepository extends JpaRepository<BoardColumn,Long> {
    List<BoardColumn> findByBoardIdOrderByPositionAsc(Long boardId);
}
