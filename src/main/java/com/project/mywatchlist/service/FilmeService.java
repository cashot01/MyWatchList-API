package com.project.mywatchlist.service;

import com.project.mywatchlist.model.Filme;
import com.project.mywatchlist.model.StatusFilme;
import com.project.mywatchlist.repository.FilmeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilmeService {

    private final FilmeRepository repository;

    public FilmeService(FilmeRepository repository) {
        this.repository = repository;
    }

    public List<Filme> listarTodos() {
        return repository.findAll();
    }

    public List<Filme> listarAssistidos() {
        return repository.findByStatus(StatusFilme.ASSISTIDO);
    }

    public List<Filme> listarWatchlist() {
        return repository.findByStatus(StatusFilme.WATCHLIST);
    }

    public void salvar(Filme filme) {
        repository.save(filme);
    }

    public Filme buscarPorId(Long id) {
        return repository.findById(id).orElseThrow();
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }

    public long totalFilmes() {
        return repository.count();
    }

    public long totalAssistidos() {
        return repository.findByStatus(StatusFilme.ASSISTIDO).size();
    }

    public long totalWatchlist() {
        return repository.findByStatus(StatusFilme.WATCHLIST).size();
    }
}
