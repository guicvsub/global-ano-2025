package com.fiap.api.dto;

import lombok.Data;
import com.fiap.api.model.Pais;

@Data
public class PaisDTO extends BaseSimplificadoDTO {
    public static PaisDTO fromEntity(Pais pais) {
        if (pais == null) return null;
        
        PaisDTO dto = new PaisDTO();
        dto.setId(pais.getId());
        dto.setNome(pais.getNome());
        return dto;
    }
} 