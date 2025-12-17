package org.example.taskservice.controller;

import lombok.AllArgsConstructor;
import org.example.taskservice.dto.ColumnDto;
import org.example.taskservice.form.ColumnCreateForm;
import org.example.taskservice.form.ColumnUpdateForm;
import org.example.taskservice.service.ColumnService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/columns")
@PreAuthorize("hasRole('User')")
public class ColumnController {
    private ColumnService columnService;

    @GetMapping
    public Page<ColumnDto> findAll(Pageable pageable) {
        return columnService.findAll(pageable);
    }

    @GetMapping("/{columnId}")
    public ColumnDto findByColumnId(@PathVariable Long columnId){
        return columnService.findByColumnId(columnId);
    }

    @PostMapping
    public ColumnDto create(@RequestBody ColumnCreateForm form){
        return columnService.create(form);
    }

    @PatchMapping("/{columnId}")
    public ColumnDto update(@PathVariable Long columnId, @RequestBody ColumnUpdateForm form){
        return columnService.update(columnId, form);
    }

    @DeleteMapping("/{columnId}/archive")
    public ColumnDto archive(@PathVariable Long columnId){
        return columnService.softDelete(columnId);
    }

    @DeleteMapping("/{columnId}/delete")
    public String delete(@PathVariable Long columnId){
        columnService.delete(columnId);
        return "Column deleted successfully";
    }
}
