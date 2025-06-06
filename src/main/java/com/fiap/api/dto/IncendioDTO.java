package com.fiap.api.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;
import lombok.Data;
import com.fiap.api.model.Incendio;

@Data
public class IncendioDTO {
    private String idIncendio;
    private Timestamp dataHoraGmt;
    private BigDecimal lat;
    private BigDecimal lon;
    private Integer diasSemChuva;
    private BigDecimal precipitacao;
    private BigDecimal riscoFogo;
    private BigDecimal frp;
    private MunicipioDTO municipio;
    private BiomaDTO bioma;
    private SateliteDTO satelite;

    public static IncendioDTO fromEntity(Incendio incendio) {
        if (incendio == null) return null;
        
        IncendioDTO dto = new IncendioDTO();
        dto.setIdIncendio(incendio.getIdIncendio());
        dto.setDataHoraGmt(incendio.getDataHoraGmt());
        dto.setLat(incendio.getLat());
        dto.setLon(incendio.getLon());
        dto.setDiasSemChuva(incendio.getDiasSemChuva());
        dto.setPrecipitacao(incendio.getPrecipitacao());
        dto.setRiscoFogo(incendio.getRiscoFogo());
        dto.setFrp(incendio.getFrp());
        
        // Using simplified DTOs
        dto.setMunicipio(MunicipioDTO.fromEntity(incendio.getMunicipio()));
        dto.setBioma(BiomaDTO.fromEntity(incendio.getBioma()));
        dto.setSatelite(SateliteDTO.fromEntity(incendio.getSatelite()));
        
        return dto;
    }
} 