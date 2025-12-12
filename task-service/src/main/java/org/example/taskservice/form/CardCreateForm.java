package org.example.taskservice.form;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardCreateForm {
    private String title;
    private String description;
    private Long columnId;
}
