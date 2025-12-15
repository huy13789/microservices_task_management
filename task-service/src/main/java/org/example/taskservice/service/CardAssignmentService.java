package org.example.taskservice.service;

import org.example.taskservice.dto.CardAssignmentDto;
import org.example.taskservice.form.CardAssignmentCreateForm;

import java.util.List;

public interface CardAssignmentService {
    List<CardAssignmentDto> findByCardId(Long cardId);
    CardAssignmentDto assignMember(CardAssignmentCreateForm form, Long cardId);
    void deleteMember(Long id);
}
