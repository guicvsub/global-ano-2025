package com.fiap.api.service;

import com.fiap.api.model.Satelite;
import com.fiap.api.repository.SateliteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SateliteService {

    @Autowired
    private SateliteRepository repository;

    @Transactional(readOnly = true)
    public List<Satelite> findAll() {
        return repository.findAll(PageRequest.of(0, 10)).getContent();
    }

    @Transactional(readOnly = true)
    public List<Satelite> findAllWithLimit(Integer limit) {
        int validLimit = Math.min(Math.max(limit, 1), 100);
        return repository.findAll(PageRequest.of(0, validLimit)).getContent();
    }

    @Transactional(readOnly = true)
    public Satelite findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Satélite não encontrado"));
    }

    @Transactional
    public Satelite create(Satelite obj) {
        return repository.save(obj);
    }

    @Transactional
    public Satelite update(Long id, Satelite obj) {
        Satelite entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Satélite não encontrado"));
        updateData(entity, obj);
        return repository.save(entity);
    }

    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

    private void updateData(Satelite entity, Satelite obj) {
        entity.setNome(obj.getNome());
    }
} 