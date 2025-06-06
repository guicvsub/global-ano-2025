package com.fiap.api.service;

import com.fiap.api.model.Municipio;
import com.fiap.api.repository.MunicipioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MunicipioService {

    @Autowired
    private MunicipioRepository repository;

    @Transactional(readOnly = true)
    public List<Municipio> findAll() {
        return repository.findAll();  // <<< Agora busca todos os registros
    }

    @Transactional(readOnly = true)
    public List<Municipio> findAllWithLimit(Integer limit) {
        int validLimit = Math.min(Math.max(limit, 1), 100);
        return repository.findAll(PageRequest.of(0, validLimit)).getContent();
    }

    @Transactional(readOnly = true)
    public Municipio findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Município não encontrado"));
    }

    @Transactional
    public Municipio create(Municipio obj) {
        return repository.save(obj);
    }

    @Transactional
    public Municipio update(Long id, Municipio obj) {
        Municipio entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Município não encontrado"));
        updateData(entity, obj);
        return repository.save(entity);
    }

    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

    private void updateData(Municipio entity, Municipio obj) {
        entity.setNome(obj.getNome());
        entity.setEstado(obj.getEstado());
    }
}
