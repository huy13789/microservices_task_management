package org.example.taskservice.service.impl;

import lombok.AllArgsConstructor;
import org.example.taskservice.dto.LabelDto;
import org.example.taskservice.exception.ResourceNotFoundException;
import org.example.taskservice.form.LabelCreateForm;
import org.example.taskservice.form.LabelUpdateForm;
import org.example.taskservice.mapper.LabelMapper;
import org.example.taskservice.model.Label;
import org.example.taskservice.model.LabelEntityType;
import org.example.taskservice.repository.BoardRepository;
import org.example.taskservice.repository.CardRepository;
import org.example.taskservice.repository.LabelRepository;
import org.example.taskservice.service.LabelService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly=true)
@AllArgsConstructor
public class LabelServiceImpl implements LabelService {

    private LabelRepository labelRepository;
    private BoardRepository boardRepository;
    private CardRepository cardRepository;

    @Override
    @Transactional
    public LabelDto create(LabelCreateForm form) {
        if (form.getEntityType() == LabelEntityType.BOARD){
            if (!boardRepository.existsById(form.getEntityId())){
                throw new ResourceNotFoundException("Board with id " + form.getEntityId() + " not found");
            }
        }
        if (form.getEntityType() == LabelEntityType.CARD){
            if (!cardRepository.existsById(form.getEntityId())){
                throw new ResourceNotFoundException("Card with id " + form.getEntityId() + " not found");
            }
        }
        Label label = LabelMapper.map(form);
        return LabelMapper.map(labelRepository.save(label));
    }

    @Override
    @Transactional
    public LabelDto update(Long labelId, LabelUpdateForm form) {
        var label = labelRepository.findById(labelId).orElseThrow(
                () -> new ResourceNotFoundException("Label not found with id: " + labelId)
        );
        LabelMapper.map(form, label);
        var updatedLabel = labelRepository.save(label);
        return LabelMapper.map(updatedLabel);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!labelRepository.existsById(id)) {
            throw new ResourceNotFoundException("Label not found with id: " + id);
        }
        labelRepository.deleteById(id);
    }

    @Override
    public List<LabelDto> getLabelsByEntity(LabelEntityType entityType, Long entityId) {
        var labels = labelRepository.findByEntityTypeAndEntityId(entityType, entityId);
        return labels.stream().map(LabelMapper::map).toList();
    }
}
