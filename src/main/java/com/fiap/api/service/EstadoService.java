package com.fiap.api.service;

import com.fiap.api.model.Estado;
import com.fiap.api.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EstadoService {

    @Autowired
    private EstadoRepository repository;

    @Transactional(readOnly = true)
    public List<Estado> findAll() {
        return repository.findAllWithoutPagination();
    }

    @Transactional(readOnly = true)
    public List<Estado> findAllWithLimit(Integer limit) {
        if (limit == null || limit <= 0) {
            return findAll();
        }
        int validLimit = Math.min(limit, 1000);
        return repository.findAll(PageRequest.of(0, validLimit)).getContent();
    }

    @Transactional(readOnly = true)
    public Estado findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estado não encontrado"));
    }

    @Transactional
    public Estado create(Estado obj) {
        return repository.save(obj);
    }

    @Transactional
    public Estado update(Long id, Estado obj) {
        Estado entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estado não encontrado"));
        updateData(entity, obj);
        return repository.save(entity);
    }

    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

    private void updateData(Estado entity, Estado obj) {
        entity.setNome(obj.getNome());
        entity.setPais(obj.getPais());
    }
} 