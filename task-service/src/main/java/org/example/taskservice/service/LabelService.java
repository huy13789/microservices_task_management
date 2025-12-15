package org.example.taskservice.service;

import org.example.taskservice.dto.LabelDto;
import org.example.taskservice.form.LabelCreateForm;
import org.example.taskservice.form.LabelUpdateForm;
import org.example.taskservice.model.LabelEntityType;

import java.util.List;

public interface LabelService {
    LabelDto create(LabelCreateForm form);
    LabelDto update(Long labelId, LabelUpdateForm form);
    void delete(Long id);
    List<LabelDto> getLabelsByEntity(LabelEntityType entityType, Long entityId);
}
