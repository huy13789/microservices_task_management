package org.example.taskservice.mapper;

import org.example.taskservice.dto.ColumnDto;
import org.example.taskservice.form.ColumnCreateForm;
import org.example.taskservice.model.Board;
import org.example.taskservice.model.BoardColumn;

public class ColumnMapper {
    // Create BoardColumn
    public static BoardColumn map(ColumnCreateForm form, Board board) {
        var column = new BoardColumn();
        map(form, column, board);

        return column;
    }

    // Response
    public static ColumnDto map(BoardColumn boardColumn){
        var columnDto = new ColumnDto();
        columnDto.setId(boardColumn.getId());
        columnDto.setTitle(boardColumn.getTitle());
        columnDto.setDescription(boardColumn.getDescription());
        columnDto.setPosition(boardColumn.getPosition());
        columnDto.setIsArchived(boardColumn.getIsArchived());
        columnDto.setBoard(BoardMapper.map(boardColumn.getBoard()));
        columnDto.setCreatedAt(boardColumn.getCreatedAt());
        columnDto.setUpdatedAt(boardColumn.getUpdatedAt());

        return columnDto;
    }

    // Modifier data
    public static void map(ColumnCreateForm form, BoardColumn boardColumn, Board board) {
        boardColumn.setTitle(form.getTitle());
        boardColumn.setDescription(form.getDescription());
        boardColumn.setBoard(board);
    }
}
