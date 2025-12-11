package org.example.taskservice.form;

import lombok.Getter;
import lombok.Setter;
import org.example.taskservice.model.BoardVisibility;

@Getter
@Setter
public class BoardUpdateForm {
    private String title;
    private String description;
    private String background;
    private BoardVisibility visibility;
}
