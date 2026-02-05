package com.project.mywatchlist.controller;

import com.project.mywatchlist.model.Filme;
import com.project.mywatchlist.service.FilmeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/filmes")
public class FilmeController {

    private final FilmeService service;

    public FilmeController(FilmeService service) {
        this.service = service;
    }

    /**
     * Lista filmes com paginação e filtros
     */
    @GetMapping
    public String listar(
            @RequestParam(required = false) String filtro,
            @RequestParam(defaultValue = "0") int page,
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
    public String novoFilme(Model model) {
        model.addAttribute("filme", new Filme());
        return "filmes/form";
    }

    /**
     * Salva novo filme
     */
    @PostMapping
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
    public String editar(@PathVariable Long id, Model model) {
        Filme filme = service.buscarPorId(id);
        model.addAttribute("filme", filme);
        return "filmes/form";
    }

    /**
     * Atualiza filme existente
     */
    @PostMapping("/editar/{id}")
    public String atualizar(@PathVariable Long id, @ModelAttribute Filme filme, Model model) {
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
    public String deletar(@PathVariable Long id, Model model) {
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