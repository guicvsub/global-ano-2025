package com.fiap.api.repository.specification;

import com.fiap.api.model.Incendio;
import com.fiap.api.model.Municipio;
import com.fiap.api.model.Estado;
import com.fiap.api.model.Bioma;
import com.fiap.api.model.Satelite;
import com.fiap.api.dto.IncendioFiltroDTO;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class IncendioSpecification {
    
    public static Specification<Incendio> comFiltros(IncendioFiltroDTO filtro) {
        return (root, query, cb) -> {
            query.distinct(true);
            List<Predicate> predicates = new ArrayList<>();
            
            // Joins
            if (filtro.getEstadoId() != null || filtro.getMunicipioId() != null) {
                Join<Incendio, Municipio> municipioJoin = root.join("municipio");
                Join<Municipio, Estado> estadoJoin = municipioJoin.join("estado");
                
                if (filtro.getEstadoId() != null) {
                    predicates.add(cb.equal(estadoJoin.get("id"), filtro.getEstadoId()));
                }
                
                if (filtro.getMunicipioId() != null) {
                    predicates.add(cb.equal(municipioJoin.get("id"), filtro.getMunicipioId()));
                }
            }
            
            if (filtro.getBiomaId() != null) {
                Join<Incendio, Bioma> biomaJoin = root.join("bioma");
                predicates.add(cb.equal(biomaJoin.get("id"), filtro.getBiomaId()));
            }
            
            if (filtro.getSateliteId() != null) {
                Join<Incendio, Satelite> sateliteJoin = root.join("satelite");
                predicates.add(cb.equal(sateliteJoin.get("id"), filtro.getSateliteId()));
            }
            
            // Filtros de data e hora
            if (filtro.getDataInicio() != null) {
                LocalDateTime dataHoraInicio = LocalDateTime.of(
                    filtro.getDataInicio(),
                    filtro.getHoraInicio() != null ? filtro.getHoraInicio() : LocalTime.MIN
                );
                predicates.add(cb.greaterThanOrEqualTo(
                    root.get("dataHoraGmt"),
                    Timestamp.valueOf(dataHoraInicio)
                ));
            }
            
            if (filtro.getDataFim() != null) {
                LocalDateTime dataHoraFim = LocalDateTime.of(
                    filtro.getDataFim(),
                    filtro.getHoraFim() != null ? filtro.getHoraFim() : LocalTime.MAX
                );
                predicates.add(cb.lessThanOrEqualTo(
                    root.get("dataHoraGmt"),
                    Timestamp.valueOf(dataHoraFim)
                ));
            }
            
            // Se não houver predicados, retorna null (sem filtros)
            if (predicates.isEmpty()) {
                return null;
            }
            
            // Combina todos os predicados com AND
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
} 