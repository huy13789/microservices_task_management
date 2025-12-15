package org.example.taskservice.service;

import org.example.taskservice.dto.BoardMemberDto;
import org.example.taskservice.form.BoardMemberCreateForm;

import java.util.List;

public interface BoardMemberService {
    List<BoardMemberDto> findByBoardId(Long boardId);
    BoardMemberDto addMember(BoardMemberCreateForm form, Long boardId);
    void deleteMember(Long id);
}
