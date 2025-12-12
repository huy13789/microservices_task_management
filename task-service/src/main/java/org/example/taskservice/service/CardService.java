package org.example.taskservice.service;

import org.example.taskservice.dto.CardDto;
import org.example.taskservice.form.CardCreateForm;
import org.example.taskservice.form.CardUpdateForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CardService {
    Page<CardDto> findAll(Pageable pageable);
    CardDto findById(Long id);
    CardDto create(CardCreateForm form);
    CardDto update(Long id, CardUpdateForm form);
    CardDto archive(Long id);
    void delete(Long id);
}
