package com.fiap.api.repository;

import com.fiap.api.model.Incendio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface IncendioRepository extends JpaRepository<Incendio, String>, JpaSpecificationExecutor<Incendio> {
} 