package com.project.mywatchlist.repository;

import com.project.mywatchlist.model.Filme;
import com.project.mywatchlist.model.StatusFilme;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FilmeRepository extends JpaRepository<Filme, Long> {
    List<Filme> findByStatus(StatusFilme status);
}
