package org.example.taskservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ColumnDto {
    private Long id;
    private String title;
    private String description;
    private Double position;
    private Boolean isArchived;
    private BoardDto board;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
