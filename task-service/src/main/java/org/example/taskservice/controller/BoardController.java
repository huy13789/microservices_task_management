package org.example.taskservice.controller;

import lombok.AllArgsConstructor;
import org.example.taskservice.dto.BoardDto;
import org.example.taskservice.form.BoardCreateForm;
import org.example.taskservice.form.BoardUpdateForm;
import org.example.taskservice.service.BoardService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/boards")
@PreAuthorize("hasRole('User')")
public class BoardController {
    private BoardService boardService;

    @GetMapping
    public Page<BoardDto> findAll(Pageable pageable) {
        return boardService.findAll(pageable);
    }

    @PostMapping
    public BoardDto create(@RequestBody BoardCreateForm form){
        return boardService.create(form);
    }

    @GetMapping("/{boardId}")
    public BoardDto findById(@PathVariable Long boardId){
        return boardService.findByBoardId(boardId);
    }

    @PatchMapping("/{boardId}")
    public BoardDto update(@PathVariable Long boardId, @RequestBody BoardUpdateForm form){
        return boardService.update(boardId, form);
    }

    @DeleteMapping("/{boardId}/close")
    public BoardDto close(@PathVariable Long boardId){
        return boardService.softDelete(boardId);
    }

    @DeleteMapping("/{boardId}/delete")
    public String delete(@PathVariable Long boardId){
        boardService.delete(boardId);
        return "Board deleted successfully";
    }
}
