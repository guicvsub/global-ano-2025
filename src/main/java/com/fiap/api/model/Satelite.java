package com.fiap.api.model;

import jakarta.persistence.*;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.List;

@Data
@Entity
@Table(name = "SATELITE", schema = "RM552321")
public class Satelite {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_satelite")
    @SequenceGenerator(
        name = "seq_satelite",
        sequenceName = "RM552321.SEQ_SATELITE",
        allocationSize = 1
    )
    @Column(name = "ID_SATELITE")
    private Long id;

    @Column(name = "NM_SATELITE", nullable = false, length = 100)
    private String nome;

    @OneToMany(mappedBy = "satelite", fetch = FetchType.LAZY)
    @JsonManagedReference("satelite-incendios")
    private List<Incendio> incendios;
}
