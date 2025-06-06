package com.fiap.api.controller;

import com.fiap.api.model.Bioma;
import com.fiap.api.service.BiomaService;
import com.fiap.api.dto.BiomaDTO;
import com.fiap.api.dto.BiomaRequestDTO;
import com.fiap.api.dto.LimitRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/biomas")
public class BiomaController {
    
    private static final Logger logger = LoggerFactory.getLogger(BiomaController.class);

    @Autowired
    private BiomaService service;

    @GetMapping
    public ResponseEntity<List<BiomaDTO>> findAll(
            @RequestParam(required = false) Integer limit,
            @RequestBody(required = false) LimitRequestDTO limitRequest) {
        
        List<Bioma> list;
        int finalLimit;
        
        if (limit != null) {
            // Use query parameter if provided
            finalLimit = Math.min(Math.max(limit, 1), 100);
        } else if (limitRequest != null) {
            // Use JSON body if provided
            finalLimit = limitRequest.getValidatedLimit();
        } else {
            // Default limit
            finalLimit = 10;
        }
        
        list = service.findAllWithLimit(finalLimit);
        List<BiomaDTO> dtoList = list.stream()
                .map(BiomaDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(dtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BiomaDTO> findById(@PathVariable Long id) {
        Bioma obj = service.findById(id);
        return ResponseEntity.ok().body(BiomaDTO.fromEntity(obj));
    }

    @PostMapping
    public ResponseEntity<BiomaDTO> create(@RequestBody BiomaRequestDTO requestDTO) {
        try {
            logger.info("Creating new Bioma with name: {}", requestDTO.getNome());
            Bioma obj = requestDTO.toEntity();
            obj = service.create(obj);
            BiomaDTO dto = BiomaDTO.fromEntity(obj);
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(obj.getId())
                    .toUri();
            return ResponseEntity.created(uri).body(dto);
        } catch (Exception e) {
            logger.error("Error creating Bioma: {}", e.getMessage(), e);
            throw new RuntimeException("Erro ao criar bioma: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<BiomaDTO> update(@PathVariable Long id, @RequestBody BiomaRequestDTO requestDTO) {
        Bioma obj = service.findById(id);
        requestDTO.updateEntity(obj);
        obj = service.update(id, obj);
        return ResponseEntity.ok().body(BiomaDTO.fromEntity(obj));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
} 