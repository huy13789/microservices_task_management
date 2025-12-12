package org.example.taskservice.service;

import org.example.taskservice.dto.ColumnDto;
import org.example.taskservice.form.ColumnCreateForm;
import org.example.taskservice.form.ColumnUpdateForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


public interface ColumnService {
    Page<ColumnDto> findAll(Pageable pageable);
    ColumnDto findByColumnId(Long id);
    ColumnDto create(ColumnCreateForm form);
    ColumnDto update(Long id, ColumnUpdateForm form);
    ColumnDto softDelete(Long id);
    void delete(Long id);
}
