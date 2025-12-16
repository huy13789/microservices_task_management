package org.example.taskservice.service.impl;

import lombok.AllArgsConstructor;
import org.example.taskservice.dto.CardDto;
import org.example.taskservice.exception.ResourceNotFoundException;
import org.example.taskservice.form.CardCreateForm;
import org.example.taskservice.form.CardUpdateForm;
import org.example.taskservice.mapper.CardMapper;
import org.example.taskservice.model.Card;
import org.example.taskservice.repository.CardRepository;
import org.example.taskservice.repository.ColumnRepository;
import org.example.taskservice.service.CardService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly=true)
@AllArgsConstructor
public class CardServiceImpl implements CardService {
    private static Double POSITION_GAP = 65536.0;
    private CardRepository cardRepository;
    private ColumnRepository columnRepository;

    @Override
    public Page<CardDto> findAll(Pageable pageable) {
        return cardRepository.findAll(pageable).map(CardMapper::map);
    }

    @Override
    public CardDto findById(Long id) {
        var card = cardRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("Card not found with id: " + id)
        );
        return CardMapper.map(card);
    }

    @Override
    @Transactional
    public CardDto create(CardCreateForm form) {
        var column = columnRepository.findById(form.getColumnId()).orElseThrow(
            () -> new ResourceNotFoundException("Column not found with id: " + form.getColumnId())
        );
        var cards = column.getCards();

        Card lastCard = null;
        if (cards != null && !cards.isEmpty()) {
            lastCard = cards.get(cards.size() - 1);
        }

        var newPosition = (lastCard != null)
                ? (lastCard.getPosition() + POSITION_GAP)
                : POSITION_GAP;
        var card = CardMapper.map(form, column);
        card.setPosition(newPosition);
        return CardMapper.map(cardRepository.save(card));
    }

    @Override
    @Transactional
    public CardDto update(Long id, CardUpdateForm form) {
        var card = cardRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("Card not found with id: " + id)
        );
        var column = columnRepository.findById(form.getColumnId()).orElseThrow(
            () -> new ResourceNotFoundException("Column not found with id: " + form.getColumnId())
        );

        if (form.getTitle() != null) {
            card.setTitle(form.getTitle());
        }
        if (form.getDescription() != null) {
            card.setDescription(form.getDescription());
        }
        card.setBoardColumn(column);
        if (form.getNewIndex() == null)
            return CardMapper.map(cardRepository.save(card));

        int targetIndex = form.getNewIndex();

        var cardsInColumn = cardRepository.findByBoardColumnIdOrderByPositionAsc(column.getId())
                .stream()
                .filter(c -> !c.getId().equals(card.getId()))
                .toList();

        Double newPosition = 0.0;
        if (cardsInColumn.isEmpty()) {
            newPosition = POSITION_GAP;
        }

        else if (targetIndex == 0)
            newPosition = cardsInColumn.get(0).getPosition() / 2.0;

        else if (targetIndex >= cardsInColumn.size())
            newPosition = cardsInColumn.get(cardsInColumn.size() - 1).getPosition() + POSITION_GAP;

        else {
            Double prevPos = cardsInColumn.get(targetIndex -1).getPosition();
            Double nextPos = cardsInColumn.get(targetIndex).getPosition();
            newPosition = (nextPos + prevPos) / 2.0;
        }

        card.setBoardColumn(column);
        card.setPosition(newPosition);

        return CardMapper.map(cardRepository.save(card));
    }

    @Override
    @Transactional
    public CardDto archive(Long id) {
        var card = cardRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("Card not found with id: " + id)
        );
        card.setIsArchived(card.getIsArchived() == false);
        return CardMapper.map(cardRepository.save(card));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        var card = cardRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("Card not found with id: " + id)
        );
        if (card.getIsArchived() == false)
            throw new IllegalArgumentException("Must be archived before deleting");
        cardRepository.delete(card);
    }
}
