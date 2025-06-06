package com.fiap.api.controller;

import com.fiap.api.model.Satelite;
import com.fiap.api.service.SateliteService;
import com.fiap.api.dto.SateliteDTO;
import com.fiap.api.dto.LimitRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/satelites")
public class SateliteController {

    @Autowired
    private SateliteService service;

    @GetMapping
    public ResponseEntity<List<SateliteDTO>> findAll(
            @RequestParam(required = false) Integer limit,
            @RequestBody(required = false) LimitRequestDTO limitRequest) {
        
        List<Satelite> list;
        if (limit != null) {
            list = service.findAllWithLimit(limit);
        } else if (limitRequest != null) {
            list = service.findAllWithLimit(limitRequest.getValidatedLimit());
        } else {
            list = service.findAll();
        }
        
        List<SateliteDTO> dtoList = list.stream()
                .map(SateliteDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(dtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SateliteDTO> findById(@PathVariable Long id) {
        Satelite obj = service.findById(id);
        return ResponseEntity.ok().body(SateliteDTO.fromEntity(obj));
    }

    @PostMapping
    public ResponseEntity<SateliteDTO> create(@RequestBody Satelite obj) {
        obj = service.create(obj);
        SateliteDTO dto = SateliteDTO.fromEntity(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(obj.getId())
                .toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SateliteDTO> update(@PathVariable Long id, @RequestBody Satelite obj) {
        obj = service.update(id, obj);
        return ResponseEntity.ok().body(SateliteDTO.fromEntity(obj));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
} 