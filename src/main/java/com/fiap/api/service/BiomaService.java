package com.fiap.api.service;

import com.fiap.api.model.Bioma;
import com.fiap.api.repository.BiomaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@Service
public class BiomaService {

    private static final Logger logger = LoggerFactory.getLogger(BiomaService.class);

    @Autowired
    private BiomaRepository repository;

    @Transactional(readOnly = true)
    public List<Bioma> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Bioma> findAllWithLimit(Integer limit) {
        if (limit == null || limit <= 0) {
            return findAll();
        }
        int validLimit = Math.min(limit, 1000);
        return repository.findAll(PageRequest.of(0, validLimit)).getContent();
    }

    @Transactional(readOnly = true)
    public Bioma findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bioma não encontrado"));
    }

    @Transactional
public Bioma create(Bioma obj) {
    try {
        logger.info("Attempting to create Bioma: {}", obj.getNome());
        obj.setId(null);  // força id null para o JPA gerar
        Bioma saved = repository.save(obj);
        logger.info("Successfully created Bioma with ID: {}", saved.getId());
        return saved;
    } catch (Exception e) {
        logger.error("Error creating Bioma: {}", e.getMessage(), e);
        throw new RuntimeException("Erro ao criar bioma no banco de dados: " + e.getMessage());
    }
}


    @Transactional
    public Bioma update(Long id, Bioma obj) {
        Bioma entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bioma não encontrado"));
        updateData(entity, obj);
        return repository.save(entity);
    }

    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

    private void updateData(Bioma entity, Bioma obj) {
        entity.setNome(obj.getNome());
    }
} 