package org.example.taskservice.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.taskservice.model.BoardVisibility;

import java.time.LocalDateTime;

@Getter
@Setter
public class BoardDto {
    private Long id;
    private String title;
    private String description;
    private String background;
    private Boolean isClosed;
    private BoardVisibility visibility;
    private String ownerId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
