package org.example.taskservice.controller;

import lombok.AllArgsConstructor;
import org.example.taskservice.dto.ColumnDto;
import org.example.taskservice.form.ColumnCreateForm;
import org.example.taskservice.form.ColumnUpdateForm;
import org.example.taskservice.service.ColumnService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class ColumnController {
    private ColumnService columnService;

    @GetMapping("/columns")
    public Page<ColumnDto> findAll(Pageable pageable) {
        return columnService.findAll(pageable);
    }

    @GetMapping("/columns/{columnId}")
    public ColumnDto findByColumnId(@PathVariable Long columnId){
        return columnService.findByColumnId(columnId);
    }

    @PostMapping("/columns")
    public ColumnDto create(@RequestBody ColumnCreateForm form){
        return columnService.create(form);
    }

    @PatchMapping("/columns/{columnId}")
    public ColumnDto update(@PathVariable Long columnId, @RequestBody ColumnUpdateForm form){
        return columnService.update(columnId, form);
    }

    @DeleteMapping("/columns/{columnId}/archive")
    public ColumnDto archive(@PathVariable Long columnId){
        return columnService.softDelete(columnId);
    }

    @DeleteMapping("/columns/{columnId}/delete")
    public String delete(@PathVariable Long columnId){
        columnService.delete(columnId);
        return "Column deleted successfully";
    }
}
