package org.example.taskservice.controller;

import lombok.AllArgsConstructor;
import org.example.taskservice.dto.CardDto;
import org.example.taskservice.form.CardCreateForm;
import org.example.taskservice.form.CardUpdateForm;
import org.example.taskservice.service.CardService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/cards")
@PreAuthorize("hasRole('User')")
public class CardController {
    private CardService cardService;

    @GetMapping
    public Page<CardDto> findAll(Pageable pageable) {
        return cardService.findAll(pageable);
    }

    @GetMapping("/{cardId}")
    public CardDto findById(@PathVariable Long cardId) {
        return cardService.findById(cardId);
    }

    @PostMapping
    public CardDto create(@RequestBody CardCreateForm form){
        return cardService.create(form);
    }

    @PatchMapping("/{cardId}")
    public CardDto update(@PathVariable Long cardId, @RequestBody CardUpdateForm form){
        return cardService.update(cardId, form);
    }

    @DeleteMapping("/{cardId}/archive")
    public CardDto archive(@PathVariable Long cardId){
        return cardService.archive(cardId);
    }

    @DeleteMapping("/{cardId}/delete")
    public String delete(@PathVariable Long cardId){
        cardService.delete(cardId);
        return "Card has been deleted";
    }
}
