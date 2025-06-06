package com.fiap.api.dto;

import lombok.Data;
import com.fiap.api.model.Estado;

@Data
public class EstadoDTO extends BaseSimplificadoDTO {
    public static EstadoDTO fromEntity(Estado estado) {
        if (estado == null) return null;
        
        EstadoDTO dto = new EstadoDTO();
        dto.setId(estado.getId());
        dto.setNome(estado.getNome());
        return dto;
    }
} 