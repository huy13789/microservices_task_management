package org.example.taskservice.service.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.taskservice.dto.BoardMemberDto;
import org.example.taskservice.exception.ResourceNotFoundException;
import org.example.taskservice.form.BoardMemberCreateForm;
import org.example.taskservice.mapper.BoardMemberMapper;
import org.example.taskservice.model.BoardMember;
import org.example.taskservice.repository.BoardMemberRepository;
import org.example.taskservice.repository.BoardRepository;
import org.example.taskservice.service.BoardMemberService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class BoardMemberServiceImpl implements BoardMemberService {

    private BoardMemberRepository boardMemberRepository;
    private BoardRepository boardRepository;

    @Override
    public List<BoardMemberDto> findByBoardId(Long boardId) {
        var board = boardRepository.findById(boardId).orElseThrow(
                () -> new ResourceNotFoundException("Card with id: " + boardId + " not found")
        );
        return board.getMembers().stream().map(BoardMemberMapper::map).toList();
    }

    @Override
    @Transactional
    public BoardMemberDto addMember(BoardMemberCreateForm form, Long boardId) {
        var board = boardRepository.findById(boardId).orElseThrow(
                () -> new ResourceNotFoundException("Board with id " + boardId + " not found")
        );
        BoardMember member = BoardMemberMapper.map(form, board);
        member.setJoinedAt(LocalDateTime.now());
        return BoardMemberMapper.map(boardMemberRepository.save(member));
    }

    @Override
    @Transactional
    public void deleteMember(Long id) {
        var member = boardMemberRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("Member's Board with id " + id + " not found")
        );
        boardMemberRepository.delete(member);
    }
}
