package org.example.taskservice.controller;

import lombok.AllArgsConstructor;
import org.example.taskservice.dto.BoardMemberDto;
import org.example.taskservice.form.BoardMemberCreateForm;
import org.example.taskservice.service.BoardMemberService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/members")
public class BoardMemberController {
    private BoardMemberService boardMemberService;

    @GetMapping("/{boardId}")
    public List<BoardMemberDto> findByBoardId(@PathVariable Long boardId) {
        return boardMemberService.findByBoardId(boardId);
    }

    @PostMapping("/{boardId}")
    public BoardMemberDto addMember(@PathVariable Long boardId, @RequestBody BoardMemberCreateForm form){
        return boardMemberService.addMember(form, boardId);
    }

    @DeleteMapping("/{boardMemberId}")
    public String deleteMember(@PathVariable Long boardMemberId){
        boardMemberService.deleteMember(boardMemberId);
        return "Member has been deleted";
    }
}
