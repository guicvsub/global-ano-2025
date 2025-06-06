package com.fiap.api.model;

import jakarta.persistence.*;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.List;

@Data
@Entity
@Table(name = "PAIS", schema = "RM552321")
public class Pais {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PAIS")
    @SequenceGenerator(name = "SEQ_PAIS", sequenceName = "SEQ_PAIS", schema = "RM552321", allocationSize = 1)
    @Column(name = "ID_PAIS")
    private Long id;

    @Column(name = "NM_PAIS", nullable = false, length = 100)
    private String nome;

    @OneToMany(mappedBy = "pais", fetch = FetchType.LAZY)
    @JsonManagedReference("pais-estados")
    private List<Estado> estados;
} 