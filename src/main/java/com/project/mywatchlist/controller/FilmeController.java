package com.project.mywatchlist.controller;

import com.project.mywatchlist.model.Filme;
import com.project.mywatchlist.service.FilmeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/filmes")
@Tag(name = "Filmes", description = "Gerenciamento de filmes da watchlist")
public class FilmeController {

    private final FilmeService service;

    public FilmeController(FilmeService service) {
        this.service = service;
    }

    /**
     * Lista filmes com paginação e filtros
     */
    @GetMapping
    @Operation(summary = "Listar filmes com paginação e filtros")
    public String listar(
            @Parameter(description = "Filtro: 'assistidos', 'watchlist' ou null para todos")
            @RequestParam(required = false) String filtro,
            @Parameter(description = "Número da página (começa em 0)")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamanho da página")
            @RequestParam(defaultValue = "5") int size,
            Model model) {

        Page<Filme> filmesPage;

        // Aplica filtro se existir
        if ("assistidos".equals(filtro)) {
            filmesPage = service.listarAssistidosPaginado(page, size);
            model.addAttribute("filtro", "assistidos");
        } else if ("watchlist".equals(filtro)) {
            filmesPage = service.listarWatchlistPaginado(page, size);
            model.addAttribute("filtro", "watchlist");
        } else {
            filmesPage = service.listarTodosPaginado(page, size);
            model.addAttribute("filtro", null);
        }

        // Adiciona dados para a view
        model.addAttribute("filmes", filmesPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", filmesPage.getTotalPages());
        model.addAttribute("totalItems", filmesPage.getTotalElements());
        model.addAttribute("pageSize", size);

        // Estatísticas
        model.addAttribute("total", service.totalFilmes());
        model.addAttribute("assistidos", service.totalAssistidos());
        model.addAttribute("watchlist", service.totalWatchlist());

        return "filmes/lista";
    }

    /**
     * Exibe formulário de novo filme
     */
    @GetMapping("/novo")
    @Operation(summary = "Exibir formulário de novo filme")
    public String novoFilme(Model model) {
        model.addAttribute("filme", new Filme());
        return "filmes/form";
    }

    /**
     * Salva novo filme
     */
    @PostMapping
    @Operation(summary = "Salvar novo filme")
    public String salvar(@ModelAttribute Filme filme, Model model) {
        try {
            service.salvar(filme);
            model.addAttribute("mensagem", "Filme salvo com sucesso!");
            model.addAttribute("sucesso", true);
            return "redirect:/filmes";
        } catch (Exception e) {
            model.addAttribute("mensagem", "Erro ao salvar filme: " + e.getMessage());
            model.addAttribute("sucesso", false);
            return "filmes/form";
        }
    }

    /**
     * Exibe formulário de edição
     */
    @GetMapping("/editar/{id}")
    @Operation(summary = "Exibir formulário de edição de filme")
    public String editar(
            @Parameter(description = "ID do filme a ser editado")
            @PathVariable Long id,
            Model model) {
        Filme filme = service.buscarPorId(id);
        model.addAttribute("filme", filme);
        return "filmes/form";
    }

    /**
     * Atualiza filme existente
     */
    @PostMapping("/editar/{id}")
    @Operation(summary = "Atualizar filme existente")
    public String atualizar(
            @Parameter(description = "ID do filme a ser atualizado")
            @PathVariable Long id,
            @ModelAttribute Filme filme,
            Model model) {
        try {
            filme.setId(id);
            service.salvar(filme);
            model.addAttribute("mensagem", "Filme atualizado com sucesso!");
            model.addAttribute("sucesso", true);
            return "redirect:/filmes";
        } catch (Exception e) {
            model.addAttribute("mensagem", "Erro ao atualizar filme: " + e.getMessage());
            model.addAttribute("sucesso", false);
            return "filmes/form";
        }
    }

    /**
     * Deleta filme
     */
    @GetMapping("/deletar/{id}")
    @Operation(summary = "Deletar filme")
    public String deletar(
            @Parameter(description = "ID do filme a ser deletado")
            @PathVariable Long id,
            Model model) {
        try {
            service.deletar(id);
            model.addAttribute("mensagem", "Filme excluído com sucesso!");
            model.addAttribute("sucesso", true);
        } catch (Exception e) {
            model.addAttribute("mensagem", "Erro ao excluir filme: " + e.getMessage());
            model.addAttribute("sucesso", false);
        }
        return "redirect:/filmes";
    }
}