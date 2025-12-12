package org.example.taskservice.service;

import org.example.taskservice.dto.CardDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CardService {
    Page<CardDto> findAll(Pageable pageable);
    CardDto findById(Long id);
    CardDto create(CardDto cardDto);
    CardDto update(CardDto cardDto);
    CardDto archive(Long id);
    void delete(Long id);
}
