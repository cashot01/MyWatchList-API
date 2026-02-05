package com.project.mywatchlist.repository;

import com.project.mywatchlist.model.Filme;
import com.project.mywatchlist.model.StatusFilme;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FilmeRepository extends JpaRepository<Filme, Long> {

    // === MÉTODOS COM PAGINAÇÃO ===
    Page<Filme> findByStatus(StatusFilme status, Pageable pageable);

    // === MÉTODOS SEM PAGINAÇÃO (mantidos) ===
    List<Filme> findByStatus(StatusFilme status);

    // === MÉTODOS DE CONTAGEM ===
    long countByStatus(StatusFilme status);
}