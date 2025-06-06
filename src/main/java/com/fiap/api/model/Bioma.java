package com.fiap.api.model;

import jakarta.persistence.*;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.List;

@Data
@Entity
@Table(name = "BIOMA", schema = "RM552321")
public class Bioma {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_bioma")
    @SequenceGenerator(
        name = "seq_bioma",
        sequenceName = "RM552321.SEQ_BIOMA",
        allocationSize = 1
    )
    @Column(name = "ID_BIOMA")
    private Long id;

    @Column(name = "NM_BIOMA", nullable = false, length = 100)
    private String nome;

    @OneToMany(mappedBy = "bioma", fetch = FetchType.LAZY)
    @JsonManagedReference("bioma-incendios")
    private List<Incendio> incendios;
} 