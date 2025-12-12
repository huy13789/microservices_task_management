package org.example.taskservice.mapper;

import org.example.taskservice.dto.CardDto;
import org.example.taskservice.form.CardCreateForm;
import org.example.taskservice.form.CardUpdateForm;
import org.example.taskservice.model.BoardColumn;
import org.example.taskservice.model.Card;

public class CardMapper {
    public static Card map(CardCreateForm form, BoardColumn column) {
        var card = new Card();
        card.setTitle(form.getTitle());
        card.setDescription(form.getDescription());
        card.setBoardColumn(column);
        return card;
    }

    public static CardDto map(Card card){
        var cardDto = new CardDto();
        cardDto.setId(card.getId());
        cardDto.setTitle(card.getTitle());
        cardDto.setDescription(card.getDescription());
        cardDto.setIsArchived(card.getIsArchived());
        cardDto.setColumn(ColumnMapper.map(card.getBoardColumn()));
        cardDto.setPosition(card.getPosition());
        cardDto.setCreatedAt(card.getCreatedAt());
        cardDto.setUpdatedAt(card.getUpdatedAt());
        return cardDto;
    }

    public static void map(CardUpdateForm form, Card card, BoardColumn column){
        card.setTitle(form.getTitle());
        card.setDescription(form.getDescription());
        card.setBoardColumn(column);
    }
}
