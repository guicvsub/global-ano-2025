package com.fiap.api.repository;

import com.fiap.api.model.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Long> {
    
    @Query(value = "SELECT * FROM RM552321.ESTADO", nativeQuery = true)
    List<Estado> findAllWithoutPagination();
} 