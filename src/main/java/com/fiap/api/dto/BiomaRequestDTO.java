package com.fiap.api.dto;

import lombok.Data;
import com.fiap.api.model.Bioma;

@Data
public class BiomaRequestDTO {
    private String nome;

    public Bioma toEntity() {
        Bioma bioma = new Bioma();
        bioma.setNome(this.nome);
        return bioma;
    }

    public void updateEntity(Bioma bioma) {
        if (this.nome != null) {
            bioma.setNome(this.nome);
        }
    }
} 