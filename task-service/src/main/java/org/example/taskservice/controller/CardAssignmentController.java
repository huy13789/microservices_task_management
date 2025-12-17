package org.example.taskservice.controller;

import lombok.AllArgsConstructor;
import org.example.taskservice.dto.CardAssignmentDto;
import org.example.taskservice.form.CardAssignmentCreateForm;
import org.example.taskservice.service.CardAssignmentService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/assignments")
@PreAuthorize("hasRole('User')")
public class CardAssignmentController {
    private CardAssignmentService cardAssignmentService;

    @GetMapping("/{cardId}")
    public List<CardAssignmentDto> findAssigneeByCard(@PathVariable Long cardId){
        return cardAssignmentService.findByCardId(cardId);
    }

    @PostMapping("/{cardId}")
    public CardAssignmentDto assignCard(@PathVariable Long cardId, @RequestBody CardAssignmentCreateForm form){
        return cardAssignmentService.assignMember(form, cardId);
    }

    @DeleteMapping("/{assignId}")
    public String deleteAssignee(@PathVariable Long assignId){
        cardAssignmentService.deleteMember(assignId);
        return "Assignment has been deleted";
    }
}
