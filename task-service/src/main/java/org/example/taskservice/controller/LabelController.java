package org.example.taskservice.controller;

import lombok.AllArgsConstructor;
import org.example.taskservice.dto.LabelDto;
import org.example.taskservice.form.LabelCreateForm;
import org.example.taskservice.form.LabelUpdateForm;
import org.example.taskservice.model.LabelEntityType;
import org.example.taskservice.service.LabelService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/labels")
@AllArgsConstructor
public class LabelController {
    private LabelService labelService;

    @GetMapping
    public List<LabelDto> getLabels(
            @RequestParam LabelEntityType entityType,
            @RequestParam Long entityId) {
        return labelService.getLabelsByEntity(entityType, entityId);
    }

    @PostMapping
    public LabelDto create(@RequestBody LabelCreateForm form) {
        return labelService.create(form);
    }

    @PatchMapping("/{id}")
    public LabelDto update(
            @PathVariable Long id,
            @RequestBody LabelUpdateForm form) {
        return labelService.update(id, form);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        labelService.delete(id);
        return "Label deleted successfully";
    }
}
