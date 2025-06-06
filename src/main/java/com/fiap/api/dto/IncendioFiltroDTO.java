package com.fiap.api.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Data;

@Data
public class IncendioFiltroDTO {
    private Long estadoId;
    private Long municipioId;
    private Long biomaId;
    private Long sateliteId;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private LocalTime horaInicio;
    private LocalTime horaFim;
} 