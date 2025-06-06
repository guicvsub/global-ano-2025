package com.fiap.api.model;

import jakarta.persistence.*;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.List;

@Data
@Entity
@Table(name = "MUNICIPIO", schema = "RM552321")
public class Municipio {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MUNICIPIO")
    @SequenceGenerator(name = "SEQ_MUNICIPIO", sequenceName = "SEQ_MUNICIPIO", schema = "RM552321", allocationSize = 1)
    @Column(name = "ID_MUNICIPIO")
    private Long id;

    @Column(name = "NM_MUNICIPIO", nullable = false, length = 100)
    private String nome;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ESTADO", nullable = false)
    @JsonBackReference("estado-municipios")
    private Estado estado;

    @OneToMany(mappedBy = "municipio", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("municipio-incendios")
    private List<Incendio> incendios;
} 