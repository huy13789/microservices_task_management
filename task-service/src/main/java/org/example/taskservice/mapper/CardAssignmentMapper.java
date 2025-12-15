package org.example.taskservice.mapper;

import org.example.taskservice.dto.CardAssignmentDto;
import org.example.taskservice.form.CardAssignmentCreateForm;
import org.example.taskservice.model.Card;
import org.example.taskservice.model.CardAssignment;

public class CardAssignmentMapper {
    public static CardAssignment map(CardAssignmentCreateForm form, Card card) {
        var assignment = new CardAssignment();
        assignment.setUserId(form.getUserId());
        assignment.setRole(form.getRole());
        assignment.setCard(card);

        return assignment;
    }

    public static CardAssignmentDto map(CardAssignment assignment) {
        var assignmentDto = new CardAssignmentDto();
        assignmentDto.setUserId(assignment.getUserId());
        assignmentDto.setRole(assignment.getRole());
        assignmentDto.setCard(CardMapper.map(assignment.getCard()));
        assignmentDto.setAssignedAt(assignment.getAssignedAt());

        return assignmentDto;
    }
}
