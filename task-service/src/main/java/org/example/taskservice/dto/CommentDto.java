package org.example.taskservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentDto {
    private Long id;
    private String content;
    private String userId;
    private CardDto card;
    private Boolean isModified;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
