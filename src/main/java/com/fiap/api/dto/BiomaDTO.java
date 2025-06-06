package com.fiap.api.dto;

import lombok.Data;
import com.fiap.api.model.Bioma;

@Data
public class BiomaDTO extends BaseSimplificadoDTO {
    public static BiomaDTO fromEntity(Bioma bioma) {
        if (bioma == null) return null;
        
        BiomaDTO dto = new BiomaDTO();
        dto.setId(bioma.getId());
        dto.setNome(bioma.getNome());
        return dto;
    }
} 