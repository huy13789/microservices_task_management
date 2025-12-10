package org.example.taskservice.form;

import lombok.Getter;
import lombok.Setter;
import org.example.taskservice.model.LabelEntityType;

@Getter
@Setter
public class LabelCreateForm {
    private String title;
    private String color;
}
