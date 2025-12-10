package org.example.taskservice.mapper;

import org.example.taskservice.dto.BoardDto;
import org.example.taskservice.form.BoardCreateForm;
import org.example.taskservice.model.Board;

public class BoardMapper {
    public static Board map(BoardCreateForm form){
        var board = new Board();
        board.setTitle(form.getTitle());
        board.setDescription(form.getDescription());
        board.setBackground(form.getBackground());
        board.setVisibility(form.getVisibility());

        return board;
    }

    public static BoardDto map(Board board){
        var boardDto = new BoardDto();
        boardDto.setId(board.getId());
        boardDto.setTitle(board.getTitle());
        boardDto.setDescription(board.getDescription());
        boardDto.setBackground(board.getBackground());
        boardDto.setIsClosed(board.getIsClosed());
        boardDto.setVisibility(board.getVisibility());
        boardDto.setOwnerId(board.getOwnerId());
        boardDto.setCreatedAt(board.getCreatedAt());
        boardDto.setUpdatedAt(board.getUpdatedAt());

        return boardDto;
    }

    public static void map(BoardCreateForm form, Board board){
        board.setTitle(form.getTitle());
        board.setDescription(form.getDescription());
        board.setBackground(form.getBackground());
        board.setIsClosed(board.getIsClosed());
        board.setVisibility(form.getVisibility());
    }
    
}
