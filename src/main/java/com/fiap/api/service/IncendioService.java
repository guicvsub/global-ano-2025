package com.fiap.api.service;
import java.util.UUID;

import com.fiap.api.model.Incendio;
import com.fiap.api.repository.IncendioRepository;
import com.fiap.api.dto.IncendioDTO;
import com.fiap.api.dto.PageableDTO;
import com.fiap.api.dto.IncendioFiltroDTO;
import com.fiap.api.repository.specification.IncendioSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IncendioService {

    private static final int DEFAULT_PAGE_SIZE = 10;
    private static final int MAX_PAGE_SIZE = 100;

    @Autowired
    private IncendioRepository repository;

    @Transactional(readOnly = true)
    public List<IncendioDTO> findAll() {
        Page<Incendio> page = repository.findAll(PageRequest.of(0, DEFAULT_PAGE_SIZE));
        return page.getContent().stream()
                .map(IncendioDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<IncendioDTO> findAllWithLimit(Integer limit) {
        int validLimit = limit != null ? Math.min(Math.max(limit, 1), MAX_PAGE_SIZE) : DEFAULT_PAGE_SIZE;
        Page<Incendio> page = repository.findAll(PageRequest.of(0, validLimit));
        return page.getContent().stream()
                .map(IncendioDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PageableDTO<IncendioDTO> buscarComFiltros(IncendioFiltroDTO filtro, Pageable pageable) {
        // Garantir que o tamanho da página está dentro dos limites
        int pageSize = Math.min(Math.max(pageable.getPageSize(), 1), MAX_PAGE_SIZE);
        PageRequest validPageRequest = PageRequest.of(pageable.getPageNumber(), pageSize, pageable.getSort());
        
        Page<Incendio> page = repository.findAll(IncendioSpecification.comFiltros(filtro), validPageRequest);
        Page<IncendioDTO> dtoPage = page.map(IncendioDTO::fromEntity);
        return PageableDTO.fromPage(dtoPage);
    }

    @Transactional(readOnly = true)
    public IncendioDTO findById(String id) {
        Incendio incendio = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Incêndio não encontrado"));
        return IncendioDTO.fromEntity(incendio);
    }
    

    @Transactional
    public IncendioDTO create(Incendio obj) {
        if (obj.getIdIncendio() == null || obj.getIdIncendio().isEmpty()) {
            obj.setIdIncendio(UUID.randomUUID().toString());
        }
        Incendio saved = repository.save(obj);
        return IncendioDTO.fromEntity(saved);
    }
    
    

    @Transactional
    public IncendioDTO update(String id, Incendio obj) {
        Incendio entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Incêndio não encontrado"));
        updateData(entity, obj);
        Incendio updated = repository.save(entity);
        return IncendioDTO.fromEntity(updated);
    }

    @Transactional
    public void delete(String id) {
        repository.deleteById(id);
    }

    private void updateData(Incendio entity, Incendio obj) {
        entity.setDataHoraGmt(obj.getDataHoraGmt());
        entity.setLat(obj.getLat());
        entity.setLon(obj.getLon());
        entity.setDiasSemChuva(obj.getDiasSemChuva());
        entity.setPrecipitacao(obj.getPrecipitacao());
        entity.setRiscoFogo(obj.getRiscoFogo());
        entity.setFrp(obj.getFrp());
        entity.setMunicipio(obj.getMunicipio());
        entity.setBioma(obj.getBioma());
        entity.setSatelite(obj.getSatelite());
    }
} 