package com.fiap.api.repository;

import com.fiap.api.model.Bioma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BiomaRepository extends JpaRepository<Bioma, Long> {
} 