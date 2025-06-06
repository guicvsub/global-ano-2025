package com.fiap.api.controller;

import com.fiap.api.model.Municipio;
import com.fiap.api.service.MunicipioService;
import com.fiap.api.service.EstadoService;
import com.fiap.api.dto.MunicipioDTO;
import com.fiap.api.dto.MunicipioRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/municipios")
public class MunicipioController {

    @Autowired
    private MunicipioService service;

    @Autowired
    private EstadoService estadoService;

    @GetMapping
    public ResponseEntity<List<MunicipioDTO>> findAll(@RequestParam(required = false) Integer limit) {
        List<Municipio> list;

        if (limit != null) {
            list = service.findAllWithLimit(limit);
        } else {
            list = service.findAll();  // <<< Traz todos os municípios
        }

        List<MunicipioDTO> dtoList = list.stream()
                .map(MunicipioDTO::fromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(dtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MunicipioDTO> findById(@PathVariable Long id) {
        Municipio obj = service.findById(id);
        return ResponseEntity.ok().body(MunicipioDTO.fromEntity(obj));
    }

    @PostMapping
    public ResponseEntity<MunicipioDTO> create(@RequestBody MunicipioRequestDTO requestDTO) {
        Municipio obj = requestDTO.toEntity(estadoService.findById(requestDTO.getEstadoId()));
        obj = service.create(obj);
        MunicipioDTO dto = MunicipioDTO.fromEntity(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(obj.getId())
                .toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MunicipioDTO> update(@PathVariable Long id, @RequestBody MunicipioRequestDTO requestDTO) {
        Municipio obj = service.findById(id);
        requestDTO.updateEntity(obj, requestDTO.getEstadoId() != null ?
                estadoService.findById(requestDTO.getEstadoId()) : null);
        obj = service.update(id, obj);
        return ResponseEntity.ok().body(MunicipioDTO.fromEntity(obj));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
