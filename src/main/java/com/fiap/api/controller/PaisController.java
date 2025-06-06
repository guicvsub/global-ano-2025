package com.fiap.api.controller;

import com.fiap.api.model.Pais;
import com.fiap.api.service.PaisService;
import com.fiap.api.dto.PaisDTO;
import com.fiap.api.dto.PaisRequestDTO;
import com.fiap.api.dto.LimitRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/paises")
public class PaisController {

    @Autowired
    private PaisService service;

    @GetMapping
    public ResponseEntity<List<PaisDTO>> findAll(@RequestBody(required = false) LimitRequestDTO limitRequest) {
        List<Pais> list;
        if (limitRequest != null) {
            list = service.findAllWithLimit(limitRequest.getValidatedLimit());
        } else {
            list = service.findAll();
        }
        List<PaisDTO> dtoList = list.stream()
                .map(PaisDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(dtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaisDTO> findById(@PathVariable Long id) {
        Pais obj = service.findById(id);
        return ResponseEntity.ok().body(PaisDTO.fromEntity(obj));
    }

    @PostMapping
    public ResponseEntity<PaisDTO> create(@RequestBody PaisRequestDTO requestDTO) {
        Pais obj = requestDTO.toEntity();
        obj = service.create(obj);
        PaisDTO dto = PaisDTO.fromEntity(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(obj.getId())
                .toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaisDTO> update(@PathVariable Long id, @RequestBody PaisRequestDTO requestDTO) {
        Pais obj = service.findById(id);
        requestDTO.updateEntity(obj);
        obj = service.update(id, obj);
        return ResponseEntity.ok().body(PaisDTO.fromEntity(obj));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
} 