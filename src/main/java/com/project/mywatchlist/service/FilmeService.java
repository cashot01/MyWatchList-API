package com.project.mywatchlist.service;

import com.project.mywatchlist.model.Filme;
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


    public Filme buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Filme não encontrado"));
    }


    public Filme salvar(Filme filme) {
        return repository.save(filme);
    }


    public void deletar(Long id) {
        repository.deleteById(id);
    }

    public long totalFilmes() {
        return repository.count();
    }

    public long totalAssistidos() {
        return repository.findByAssistidoTrue().size();
    }

    public long totalWatchlist() {
        return repository.findByNaWatchlistTrue().size();
    }

    public List<Filme> listarAssistidos() {
        return repository.findByAssistidoTrue();
    }

    public List<Filme> listarWatchlist() {
        return repository.findByNaWatchlistTrue();
    }
}
