package org.example.taskservice.form;

import lombok.Getter;
import lombok.Setter;
import org.example.taskservice.model.BoardVisibility;

@Getter
@Setter
public class BoardCreateForm {
    private String title;
    private String description;
    private String background;
    private BoardVisibility visibility;
}
