package com.project.mywatchlist.service;

import com.project.mywatchlist.model.Filme;
import com.project.mywatchlist.model.StatusFilme;
import com.project.mywatchlist.repository.FilmeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilmeService {

    private final FilmeRepository repository;

    public FilmeService(FilmeRepository repository) {
        this.repository = repository;
    }

    // === MÉTODOS COM PAGINAÇÃO ===

    /**
     * Lista todos os filmes com paginação
     * @param page Número da página (começa em 0)
     * @param size Quantidade de itens por página
     * @return Página de filmes
     */
    public Page<Filme> listarTodosPaginado(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findAll(pageable);
    }

    /**
     * Lista filmes assistidos com paginação
     * @param page Número da página
     * @param size Quantidade de itens por página
     * @return Página de filmes assistidos
     */
    public Page<Filme> listarAssistidosPaginado(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findByStatus(StatusFilme.ASSISTIDO, pageable);
    }

    /**
     * Lista filmes na watchlist com paginação
     * @param page Número da página
     * @param size Quantidade de itens por página
     * @return Página de filmes na watchlist
     */
    public Page<Filme> listarWatchlistPaginado(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findByStatus(StatusFilme.WATCHLIST, pageable);
    }

    // === MÉTODOS ANTIGOS (mantidos para compatibilidade) ===

    public List<Filme> listarTodos() {
        return repository.findAll();
    }

    public List<Filme> listarAssistidos() {
        return repository.findByStatus(StatusFilme.ASSISTIDO);
    }

    public List<Filme> listarWatchlist() {
        return repository.findByStatus(StatusFilme.WATCHLIST);
    }

    // === MÉTODOS DE CRUD ===

    public void salvar(Filme filme) {
        repository.save(filme);
    }

    public Filme buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Filme não encontrado com ID: " + id));
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }

    // === MÉTODOS DE ESTATÍSTICAS ===

    public long totalFilmes() {
        return repository.count();
    }

    public long totalAssistidos() {
        return repository.countByStatus(StatusFilme.ASSISTIDO);
    }

    public long totalWatchlist() {
        return repository.countByStatus(StatusFilme.WATCHLIST);
    }
}
