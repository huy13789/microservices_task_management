package org.example.taskservice.service.impl;

import lombok.AllArgsConstructor;
import org.example.taskservice.dto.CardAssignmentDto;
import org.example.taskservice.exception.ResourceNotFoundException;
import org.example.taskservice.form.CardAssignmentCreateForm;
import org.example.taskservice.mapper.CardAssignmentMapper;
import org.example.taskservice.model.BoardMember;
import org.example.taskservice.model.CardAssignment;
import org.example.taskservice.repository.BoardRepository;
import org.example.taskservice.repository.CardAssignmentRepository;
import org.example.taskservice.repository.CardRepository;
import org.example.taskservice.service.CardAssignmentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
@Transactional(readOnly=true)
public class CardAssignmentServiceImpl implements CardAssignmentService {
    private CardAssignmentRepository cardAssignmentRepository;
    private CardRepository cardRepository;
    private BoardRepository boardRepository;

    @Override
    public List<CardAssignmentDto> findByCardId(Long cardId) {
        var card = cardRepository.findById(cardId).orElseThrow(
                () -> new ResourceNotFoundException("Card with id " + cardId + " not found")
        );
        return card.getAssignments().stream().map(CardAssignmentMapper::map).toList();
    }

    @Override
    @Transactional
    public CardAssignmentDto assignMember(CardAssignmentCreateForm form, Long cardId) {
        var card = cardRepository.findById(cardId).orElseThrow(
            () -> new ResourceNotFoundException("Card with id " + cardId + " not found")
        );
        var board = boardRepository.findById(card.getBoardColumn().getBoard().getId()).orElseThrow(
                () -> new ResourceNotFoundException("Board with id " + card.getBoardColumn().getBoard().getId() + " not found")
        );
        for (BoardMember member : board.getMembers()){
            if (!Objects.equals(member.getUserId(), form.getUserId())){
                throw new ResourceNotFoundException("User with id " + member.getUserId() + " not found at Board with id " + card.getBoardColumn().getBoard().getId());
            }
        }
        CardAssignment assignee = CardAssignmentMapper.map(form, card);
        assignee.setAssignedAt(LocalDateTime.now());
        return CardAssignmentMapper.map(cardAssignmentRepository.save(assignee));
    }

    @Override
    @Transactional
    public void deleteMember(Long id) {
        var assignee = cardAssignmentRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("Assignee with id " + id + " not found")
        );
        cardAssignmentRepository.delete(assignee);
    }
}
