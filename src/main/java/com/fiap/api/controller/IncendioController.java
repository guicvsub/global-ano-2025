package com.fiap.api.controller;

import com.fiap.api.model.Incendio;
import com.fiap.api.service.IncendioService;
import com.fiap.api.dto.IncendioDTO;
import com.fiap.api.dto.PageableDTO;
import com.fiap.api.dto.IncendioFiltroDTO;
import com.fiap.api.dto.LimitRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/incendios")
public class IncendioController {

    @Autowired
    private IncendioService service;

    @GetMapping("/busca")
    public ResponseEntity<PageableDTO<IncendioDTO>> buscarComFiltros(
            @RequestParam(required = false) Long estadoId,
            @RequestParam(required = false) Long municipioId,
            @RequestParam(required = false) Long biomaId,
            @RequestParam(required = false) Long sateliteId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime horaInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime horaFim,
            @RequestBody(required = false) LimitRequestDTO limitRequest,
            @PageableDefault(size = 10) Pageable pageable) {
        
        IncendioFiltroDTO filtro = new IncendioFiltroDTO();
        filtro.setEstadoId(estadoId);
        filtro.setMunicipioId(municipioId);
        filtro.setBiomaId(biomaId);
        filtro.setSateliteId(sateliteId);
        filtro.setDataInicio(dataInicio);
        filtro.setDataFim(dataFim);
        filtro.setHoraInicio(horaInicio);
        filtro.setHoraFim(horaFim);
        
        if (limitRequest != null) {
            pageable = PageRequest.of(0, limitRequest.getValidatedLimit());
        }
        
        return ResponseEntity.ok(service.buscarComFiltros(filtro, pageable));
    }

    @GetMapping
    public ResponseEntity<List<IncendioDTO>> findAll(
            @RequestParam(required = false, defaultValue = "10") Integer limit) {
        return ResponseEntity.ok(service.findAllWithLimit(limit));
    }

    @GetMapping("/{id}")
    public ResponseEntity<IncendioDTO> findById(@PathVariable String id) {
        IncendioDTO dto = service.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    public ResponseEntity<IncendioDTO> create(@RequestBody Incendio obj) {
        IncendioDTO dto = service.create(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(dto.getIdIncendio())
                .toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<IncendioDTO> update(@PathVariable String id, @RequestBody Incendio obj) {
        IncendioDTO dto = service.update(id, obj);
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
} 