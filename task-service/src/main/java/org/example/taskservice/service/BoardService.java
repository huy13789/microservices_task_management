package org.example.taskservice.service;

import org.example.taskservice.dto.BoardDto;
import org.example.taskservice.form.BoardCreateForm;
import org.example.taskservice.form.BoardUpdateForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface BoardService {
    Page<BoardDto> findAll(Pageable pageable);
    BoardDto findByBoardId(Long id);
    BoardDto create(BoardCreateForm form);
    BoardDto update(Long id, BoardUpdateForm form);
    BoardDto softDelete(Long id);
    void delete(Long id);
}
