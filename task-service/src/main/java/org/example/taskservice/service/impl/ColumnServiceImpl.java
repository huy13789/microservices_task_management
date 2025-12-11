package org.example.taskservice.service.impl;

import lombok.AllArgsConstructor;
import org.example.taskservice.dto.ColumnDto;
import org.example.taskservice.exception.ResourceNotFoundException;
import org.example.taskservice.form.ColumnCreateForm;
import org.example.taskservice.form.ColumnUpdateForm;
import org.example.taskservice.mapper.ColumnMapper;
import org.example.taskservice.model.BoardColumn;
import org.example.taskservice.repository.BoardRepository;
import org.example.taskservice.repository.ColumnRepository;
import org.example.taskservice.service.ColumnService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class ColumnServiceImpl implements ColumnService {
    private static double POSITION_GAP = 65536.0;
    private ColumnRepository columnRepository;
    private BoardRepository boardRepository;

    @Override
    public Page<ColumnDto> findAll(Pageable pageable) {
        return columnRepository.findAll(pageable).map(ColumnMapper::map);
    }

    @Override
    public ColumnDto findByColumnId(Long id) {
        var column = columnRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("Column with id " + id + " not found")
        );
        return ColumnMapper.map(column);
    }

    @Override
    @Transactional
    public ColumnDto create(ColumnCreateForm form) {
        var board = boardRepository.findById(form.getBoardId()).orElseThrow(
            () -> new ResourceNotFoundException("Board with id " + form.getBoardId() + " not found")
        );
        var columns = board.getColumns();

        BoardColumn lastColumn = null;
        if (columns != null && !columns.isEmpty()){
            lastColumn = columns.get(columns.size() - 1);
        }

        var newPosition = (lastColumn != null)
                ? lastColumn.getPosition() + POSITION_GAP
                : POSITION_GAP;

        var column = ColumnMapper.map(form, board);
        column.setPosition(newPosition);
        return ColumnMapper.map(columnRepository.save(column));
    }

    @Override
    @Transactional
    public ColumnDto update(Long id, ColumnUpdateForm form) {
        // Get column update
        var column = columnRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("Column with id " + id + " not found")
        );
        // Get new board to update
        var board = boardRepository.findById(form.getBoardId()).orElseThrow(
            () -> new ResourceNotFoundException("Board with id " + form.getBoardId() + " not found")
        );

        if (form.getTitle() != null) column.setTitle(form.getTitle());
        if (form.getDescription() != null) column.setDescription(form.getDescription());
        column.setBoard(board);

        if (form.getNewIndex() == null)
            return ColumnMapper.map(columnRepository.save(column));

        // Update position
        int targetIndex = form.getNewIndex();
        var otherColumns = columnRepository.findByBoardIdOrderByPositionAsc(board.getId())
                .stream()
                .filter(c -> !c.getId().equals(column.getId()))
                .toList();
        double newPosition;
        if (otherColumns.isEmpty()) {
            newPosition = POSITION_GAP;
        }
        else if (targetIndex == 0) {
            newPosition = otherColumns.get(0).getPosition() / 2.0;
        }
        else if (targetIndex >= otherColumns.size()) {
            newPosition = otherColumns.get(otherColumns.size() - 1).getPosition() + POSITION_GAP;
        }
        else {
            var prevPos = otherColumns.get(targetIndex - 1).getPosition();
            var nextPos = otherColumns.get(targetIndex).getPosition();
            newPosition = (prevPos + nextPos)/2.0;
        }

        column.setPosition(newPosition);
        return ColumnMapper.map(columnRepository.save(column));
    }

    @Override
    @Transactional
    public ColumnDto softDelete(Long id) {
        var column = columnRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("Column with id " + id + " not found")
        );

        if (column.getIsArchived() == false)
            column.setIsArchived(true);
        else
            column.setIsArchived(false);

        return ColumnMapper.map(columnRepository.save(column));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        var column = columnRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("Column with id " + id + " not found")
        );
        if (column.getIsArchived() == false) {
            throw new IllegalArgumentException("Must be archived before deleting");
        }

        columnRepository.delete(column);
    }
}
