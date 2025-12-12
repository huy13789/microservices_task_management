package org.example.taskservice.form;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardUpdateForm {
    private String title;
    private String description;
    private Long boardId;
    private Integer newIndex;
}
