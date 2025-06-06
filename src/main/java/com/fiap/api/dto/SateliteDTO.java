package com.fiap.api.dto;

import lombok.Data;
import com.fiap.api.model.Satelite;

@Data
public class SateliteDTO extends BaseSimplificadoDTO {
    public static SateliteDTO fromEntity(Satelite satelite) {
        if (satelite == null) return null;
        
        SateliteDTO dto = new SateliteDTO();
        dto.setId(satelite.getId());
        dto.setNome(satelite.getNome());
        return dto;
    }
} 