package org.example.taskservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BoardMemberDto {
    private Long id;
    private BoardDto board;
    private String userId;
    private String role;
    private LocalDateTime joinedAt;
}
