package org.example.taskservice.repository;

import org.example.taskservice.model.Label;
import org.example.taskservice.model.LabelEntityType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LabelRepository extends JpaRepository<Label, Long> {
    List<Label> findByEntityTypeAndEntityId(LabelEntityType entityType, Long entityId);
    boolean existsByEntityTypeAndEntityIdAndTitle(LabelEntityType entityType, Long entityId, String title);
}
