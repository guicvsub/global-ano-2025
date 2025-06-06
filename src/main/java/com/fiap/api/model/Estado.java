package com.fiap.api.model;

import jakarta.persistence.*;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.List;

@Data
@Entity
@Table(name = "ESTADO", schema = "RM552321")
public class Estado {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ESTADO")
    @SequenceGenerator(name = "SEQ_ESTADO", sequenceName = "SEQ_ESTADO", schema = "RM552321", allocationSize = 1)
    @Column(name = "ID_ESTADO")
    private Long id;

    @Column(name = "NM_ESTADO", nullable = false, length = 100)
    private String nome;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PAIS", nullable = false)
    @JsonBackReference("pais-estados")
    private Pais pais;

    @OneToMany(mappedBy = "estado", fetch = FetchType.LAZY)
    @JsonManagedReference("estado-municipios")
    private List<Municipio> municipios;
} 