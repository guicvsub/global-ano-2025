package com.fiap.api.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "INCENDIO", schema = "RM552321")
public class Incendio {

    @Id
    @Column(name = "ID_INCENDIO", length = 36, columnDefinition = "CHAR(36)")
    private String idIncendio;  // UUID como String

    @Column(name = "DATA_HORA_GMT", nullable = false)
    private Timestamp dataHoraGmt;

    @Column(name = "LAT", precision = 9, scale = 6, nullable = false)
    private BigDecimal lat;

    @Column(name = "LON", precision = 9, scale = 6, nullable = false)
    private BigDecimal lon;

    @Column(name = "DIAS_SEM_CHUVA", precision = 3, scale = 0)
    private Integer diasSemChuva;

    @Column(name = "PRECIPITACAO", precision = 5, scale = 2)
    private BigDecimal precipitacao;

    @Column(name = "RISCO_FOGO", precision = 5, scale = 2)
    private BigDecimal riscoFogo;

    @Column(name = "FRP", precision = 5, scale = 2)
    private BigDecimal frp;

    // Relacionamentos

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_SATELITE", nullable = false)
    @JsonBackReference("satelite-incendios")
    private Satelite satelite;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_MUNICIPIO", nullable = false)
    @JsonBackReference("municipio-incendios")
    private Municipio municipio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_BIOMA", nullable = false)
    @JsonBackReference("bioma-incendios")
    private Bioma bioma;

    // Construtor padrão
    public Incendio() {
    }

    // Getters e Setters

    public String getIdIncendio() {
        return idIncendio;
    }

    public void setIdIncendio(String idIncendio) {
        this.idIncendio = idIncendio;
    }

    public Timestamp getDataHoraGmt() {
        return dataHoraGmt;
    }

    public void setDataHoraGmt(Timestamp dataHoraGmt) {
        this.dataHoraGmt = dataHoraGmt;
    }

    public BigDecimal getLat() {
        return lat;
    }

    public void setLat(BigDecimal lat) {
        this.lat = lat;
    }

    public BigDecimal getLon() {
        return lon;
    }

    public void setLon(BigDecimal lon) {
        this.lon = lon;
    }

    public Integer getDiasSemChuva() {
        return diasSemChuva;
    }

    public void setDiasSemChuva(Integer diasSemChuva) {
        this.diasSemChuva = diasSemChuva;
    }

    public BigDecimal getPrecipitacao() {
        return precipitacao;
    }

    public void setPrecipitacao(BigDecimal precipitacao) {
        this.precipitacao = precipitacao;
    }

    public BigDecimal getRiscoFogo() {
        return riscoFogo;
    }

    public void setRiscoFogo(BigDecimal riscoFogo) {
        this.riscoFogo = riscoFogo;
    }

    public BigDecimal getFrp() {
        return frp;
    }

    public void setFrp(BigDecimal frp) {
        this.frp = frp;
    }

    public Satelite getSatelite() {
        return satelite;
    }

    public void setSatelite(Satelite satelite) {
        this.satelite = satelite;
    }

    public Municipio getMunicipio() {
        return municipio;
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }

    public Bioma getBioma() {
        return bioma;
    }

    public void setBioma(Bioma bioma) {
        this.bioma = bioma;
    }
}
