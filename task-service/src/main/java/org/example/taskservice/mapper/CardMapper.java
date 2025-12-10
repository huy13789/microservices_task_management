package org.example.taskservice.mapper;

import org.example.taskservice.dto.CardDto;
import org.example.taskservice.form.CardCreateForm;
import org.example.taskservice.model.BoardColumn;
import org.example.taskservice.model.Card;

public class CardMapper {
    public static Card map(CardCreateForm form, BoardColumn column) {
        var card = new Card();
        map(form, card, column);

        return card;
    }

    public static CardDto map(Card card){
        var cardDto = new CardDto();
        cardDto.setId(card.getId());
        cardDto.setTitle(card.getTitle());
        cardDto.setDescription(card.getDescription());
        cardDto.setIsArchived(card.getIsArchived());
        cardDto.setCreatedAt(card.getCreatedAt());
        cardDto.setUpdatedAt(card.getUpdatedAt());

        return cardDto;
    }

    public static void map(CardCreateForm form, Card card, BoardColumn column){
        card.setTitle(form.getTitle());
        card.setDescription(form.getDescription());
        card.setBoardColumn(column);
    }
}
