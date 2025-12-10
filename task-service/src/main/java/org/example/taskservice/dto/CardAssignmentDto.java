package org.example.taskservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CardAssignmentDto {
    private Long id;
    private CardDto card;
    private String userId;
    private String role;
    private LocalDateTime assignedAt;
}
