package org.example.taskservice.service.impl;

import lombok.AllArgsConstructor;
import org.example.taskservice.dto.BoardDto;
import org.example.taskservice.exception.ResourceNotFoundException;
import org.example.taskservice.form.BoardCreateForm;
import org.example.taskservice.form.BoardUpdateForm;
import org.example.taskservice.mapper.BoardMapper;
import org.example.taskservice.repository.BoardRepository;
import org.example.taskservice.service.BoardService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class BoardServiceImpl implements BoardService {
    private BoardRepository boardRepository;

    @Override
    public Page<BoardDto> findAll(Pageable pageable) {
        return boardRepository.findAll(pageable).map(BoardMapper::map);
    }

    @Override
    @Transactional
    public BoardDto create(BoardCreateForm form) {
        var board = BoardMapper.map(form);
        board.setOwnerId("uuid-123-456");
        var saveBoard = boardRepository.save(board);
        return BoardMapper.map(saveBoard);
    }

    @Override
    public BoardDto findByBoardId(Long id) {
        var board = boardRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("Board with id " + id + " not found")
        );
        return BoardMapper.map(board);
    }

    @Override
    @Transactional
    public BoardDto update(Long id, BoardUpdateForm form) {
        var board = boardRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("Board with id " + id + " not found")
        );
        BoardMapper.map(form, board);
        var saveBoard = boardRepository.save(board);
        return BoardMapper.map(saveBoard);
    }

    @Override
    @Transactional
    public BoardDto softDelete(Long id) {
        var board = boardRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("Board with id " + id + " not found")
        );

        if (board.getIsClosed() == false)
            board.setIsClosed(true);
        else
            board.setIsClosed(false);

        return BoardMapper.map(boardRepository.save(board));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        var board = boardRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("Board with id " + id + " not found")
        );
        if (board.getIsClosed() == false) {
            throw new IllegalArgumentException("Must be closed before deleting this board");
        }
        boardRepository.delete(board);
    }
}
