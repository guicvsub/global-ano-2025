package com.fiap.api.repository;

import com.fiap.api.model.Satelite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SateliteRepository extends JpaRepository<Satelite, Long> {
} 