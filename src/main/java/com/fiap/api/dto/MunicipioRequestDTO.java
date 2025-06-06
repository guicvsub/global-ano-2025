package com.fiap.api.dto;

import lombok.Data;
import com.fiap.api.model.Municipio;
import com.fiap.api.model.Estado;

@Data
public class MunicipioRequestDTO {
    private String nome;
    private Long estadoId;

    public Municipio toEntity(Estado estado) {
        Municipio municipio = new Municipio();
        municipio.setNome(this.nome);
        municipio.setEstado(estado);
        return municipio;
    }

    public void updateEntity(Municipio municipio, Estado estado) {
        if (this.nome != null) {
            municipio.setNome(this.nome);
        }
        if (estado != null) {
            municipio.setEstado(estado);
        }
    }
} 