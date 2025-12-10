package org.example.taskservice.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.taskservice.model.LabelEntityType;

@Getter
@Setter
public class LabelDto {
    private Long id;
    private String title;
    private String color;
    private LabelEntityType entityType;
    private Long entityId;
}
