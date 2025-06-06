package com.fiap.api.dto;

import lombok.Data;
import com.fiap.api.model.Municipio;

@Data
public class MunicipioDTO extends BaseSimplificadoDTO {
    public static MunicipioDTO fromEntity(Municipio municipio) {
        if (municipio == null) return null;
        
        MunicipioDTO dto = new MunicipioDTO();
        dto.setId(municipio.getId());
        dto.setNome(municipio.getNome());
        return dto;
    }
} 