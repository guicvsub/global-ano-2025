package com.fiap.api.dto;

import lombok.Data;
import com.fiap.api.model.Pais;

@Data
public class PaisRequestDTO {
    private String nome;

    public Pais toEntity() {
        Pais pais = new Pais();
        pais.setNome(this.nome);
        return pais;
    }

    public void updateEntity(Pais pais) {
        if (this.nome != null) {
            pais.setNome(this.nome);
        }
    }
} 