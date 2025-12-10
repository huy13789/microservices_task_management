package org.example.taskservice.mapper;

import org.example.taskservice.dto.LabelDto;
import org.example.taskservice.form.LabelCreateForm;
import org.example.taskservice.model.Label;

public class LabelMapper {
    public static Label map(LabelCreateForm form) {
        var label = new Label();
        label.setTitle(form.getTitle());
        label.setColor(form.getColor());

        return label;
    }

    public static LabelDto map(Label label) {
        var labelDto = new LabelDto();
        labelDto.setId(label.getId());
        labelDto.setTitle(label.getTitle());
        labelDto.setColor(label.getColor());
        labelDto.setEntityType(label.getEntityType());
        labelDto.setEntityId(label.getEntityId());

        return labelDto;
    }

    public static void map(LabelCreateForm form, Label label) {
        label.setTitle(form.getTitle());
        label.setColor(form.getColor());
    }
}
