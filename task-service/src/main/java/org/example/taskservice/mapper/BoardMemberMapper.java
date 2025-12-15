package org.example.taskservice.mapper;

import org.example.taskservice.dto.BoardMemberDto;
import org.example.taskservice.form.BoardMemberCreateForm;
import org.example.taskservice.model.Board;
import org.example.taskservice.model.BoardMember;

public class BoardMemberMapper {
    public static BoardMember map(BoardMemberCreateForm form, Board board){
        var boardMember = new BoardMember();
        boardMember.setUserId(form.getUserId());
        boardMember.setRole(form.getRole());
        boardMember.setBoard(board);
        return boardMember;
    }

    public static BoardMemberDto map(BoardMember boardMember){
        var boardMemberDto = new BoardMemberDto();
        boardMemberDto.setUserId(boardMember.getUserId());
        boardMemberDto.setBoard(BoardMapper.map(boardMember.getBoard()));
        boardMemberDto.setUserId(boardMember.getUserId());
        boardMemberDto.setRole(boardMember.getRole());
        boardMemberDto.setJoinedAt(boardMember.getJoinedAt());
        return boardMemberDto;
    }
}
