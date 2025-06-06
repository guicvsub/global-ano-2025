package com.fiap.api.controller;

import com.fiap.api.model.Estado;
import com.fiap.api.service.EstadoService;
import com.fiap.api.service.PaisService;
import com.fiap.api.dto.EstadoDTO;
import com.fiap.api.dto.EstadoRequestDTO;
import com.fiap.api.dto.LimitRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/estados")
public class EstadoController {

    @Autowired
    private EstadoService service;

    @Autowired
    private PaisService paisService;

    @GetMapping
    public ResponseEntity<List<EstadoDTO>> findAll(
            @RequestParam(required = false) Integer limit) {
        
        List<Estado> list;
        if (limit != null && limit > 0) {
            list = service.findAllWithLimit(limit);
        } else {
            list = service.findAll(); // Retorna todos os registros sem limitação
        }
        
        List<EstadoDTO> dtoList = list.stream()
                .map(EstadoDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(dtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstadoDTO> findById(@PathVariable Long id) {
        Estado obj = service.findById(id);
        return ResponseEntity.ok().body(EstadoDTO.fromEntity(obj));
    }

    @PostMapping
    public ResponseEntity<EstadoDTO> create(@RequestBody EstadoRequestDTO requestDTO) {
        Estado obj = requestDTO.toEntity(paisService.findById(requestDTO.getPaisId()));
        obj = service.create(obj);
        EstadoDTO dto = EstadoDTO.fromEntity(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(obj.getId())
                .toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstadoDTO> update(@PathVariable Long id, @RequestBody EstadoRequestDTO requestDTO) {
        Estado obj = service.findById(id);
        requestDTO.updateEntity(obj, requestDTO.getPaisId() != null ? 
            paisService.findById(requestDTO.getPaisId()) : null);
        obj = service.update(id, obj);
        return ResponseEntity.ok().body(EstadoDTO.fromEntity(obj));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
} 