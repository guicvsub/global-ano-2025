package com.fiap.api.dto;

import lombok.Data;
import com.fiap.api.model.Estado;
import com.fiap.api.model.Pais;

@Data
public class EstadoRequestDTO {
    private String nome;
    private Long paisId;

    public Estado toEntity(Pais pais) {
        Estado estado = new Estado();
        estado.setNome(this.nome);
        estado.setPais(pais);
        return estado;
    }

    public void updateEntity(Estado estado, Pais pais) {
        if (this.nome != null) {
            estado.setNome(this.nome);
        }
        if (pais != null) {
            estado.setPais(pais);
        }
    }
} 