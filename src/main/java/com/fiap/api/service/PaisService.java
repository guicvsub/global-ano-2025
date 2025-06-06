package com.fiap.api.service;

import com.fiap.api.model.Pais;
import com.fiap.api.repository.PaisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PaisService {

    @Autowired
    private PaisRepository repository;

    @Transactional(readOnly = true)
    public List<Pais> findAll() {
        return repository.findAll(PageRequest.of(0, 10)).getContent();
    }

    @Transactional(readOnly = true)
    public List<Pais> findAllWithLimit(Integer limit) {
        int validLimit = Math.min(Math.max(limit, 1), 100);
        return repository.findAll(PageRequest.of(0, validLimit)).getContent();
    }

    @Transactional(readOnly = true)
    public Pais findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("País não encontrado"));
    }

    @Transactional
    public Pais create(Pais obj) {
        return repository.save(obj);
    }

    @Transactional
    public Pais update(Long id, Pais obj) {
        Pais entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("País não encontrado"));
        updateData(entity, obj);
        return repository.save(entity);
    }

    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

    private void updateData(Pais entity, Pais obj) {
        entity.setNome(obj.getNome());
    }
} 